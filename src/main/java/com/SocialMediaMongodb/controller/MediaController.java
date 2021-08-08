package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.*;
import com.SocialMediaMongodb.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MediaController {
    private static final String BASE_DIR = "src/main/resources/static/music-files/";

    @Autowired
    private SocialMediaService service;


    @GetMapping("/medias")
    public ModelAndView getAllMedia(HttpServletRequest request) {
        Map<Media, Artist> topMedia = new HashMap();
        Map<Media, Artist> map = new HashMap();
        HttpSession session = request.getSession();

        ModelAndView model = new ModelAndView();
        List<Media> medias = service.getAllMedia();

        if (medias.size() > 3)
            for (int i = 0; i < 3; i++) {
                Media media = medias.remove(0);
                topMedia.put(media, service.getByAlbum(media.getAlbum()).get(0).getArtist());
            }

        for (int i = 0; i < medias.size(); i++) {
            Media media = medias.get(i);
            map.put(media, service.getByAlbum(media.getAlbum()).get(0).getArtist());
        }

        model.addObject("topMedias", topMedia);
        model.addObject("medias", map);
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("playlist");
        return model;
    }

    @GetMapping("/rest/media/getAll")
    public ResponseEntity getAllMediaRest() {
        return new ResponseEntity<>(service.getAllMedia(), HttpStatus.OK);
    }

    @GetMapping("/media/{id}")
    public ModelAndView getMedia(HttpServletRequest request, @PathVariable String id) {
        HttpSession session = request.getSession();
        long viewCount = 0;

        Media media = service.getMedia(id);
        if (session.getAttribute("userID") != null)
            viewCount = setView(service.getUser((String) session.getAttribute("userID")), media);
        System.out.println("view count: " + viewCount);

        ModelAndView model = new ModelAndView();
        model.addObject("media", media);
        List<AlbumArtist> albumArtists = service.getByAlbum(media.getAlbum());
        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < albumArtists.size(); i++)
            artists.add(albumArtists.get(i).getArtist());
        model.addObject("artists", artists);
        model.addObject("viewCount", viewCount);
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("media");

        return model;
    }


    @GetMapping("/rest/media/get/{id}")
    public ResponseEntity getMediaRest(@PathVariable String id, HttpServletRequest request) {
        HttpSession session = request.getSession();

        Media media = service.getMedia(id);
        long viewCount = setView(service.getUser((String) session.getAttribute("userID")), media);
        System.out.println("view count: " + viewCount);

        if (media == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity(media, HttpStatus.OK);
    }

    public long setView(User user, Media media) {
        if (user != null && media != null)
            service.checkDuplicatedView(user, media);
        return service.getViewsCount(media);
    }

    @GetMapping("/media/download/{id}")
    public ResponseEntity downloadMedia(@PathVariable String id) throws IOException {
        Media media = service.getMedia(id);
        String mediaName = media.getName();

        Path path = Paths.get(BASE_DIR + media.getPath());
        String mimeType = Files.probeContentType(path);

        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaName + "\"")
                .contentLength(resource.contentLength())
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(resource);
    }

    @PostMapping("/media/upload")
    public ResponseEntity addMedia(@RequestParam("file") MultipartFile file, @RequestParam("albumName") String albumName,
                                   @RequestParam("mediaName") String mediaName, @RequestParam("genre") String genre) {
        Media uploadMedia = new Media();
        if (file.isEmpty()) {
            return (ResponseEntity) ResponseEntity.noContent();
        }

        try {
            // Get the file and save it somewhere
            Path path = Paths.get(BASE_DIR + file.getOriginalFilename());
            Files.write(path, file.getBytes());

            Album album = service.getAlbumByName(albumName);
            if (album == null) {
                System.out.println("The required album is not exists !!");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            uploadMedia.setName(mediaName);
            uploadMedia.setAlbum(album);
            uploadMedia.setLength(convertToMinutes(file.getSize()));
            uploadMedia.setGenre(genre);
            uploadMedia.setPath(file.getOriginalFilename());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            uploadMedia.setPublishDate(formatter.format(date));

            service.addOrUpdateMedia(uploadMedia);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(uploadMedia, HttpStatus.OK);
    }

    public String convertToMinutes(long length) {
        int mili = (int) (length / 1000);
        int sec = (mili / 1000) % 60;
        int min = (mili / 1000) / 60;
        return sec + ":" + min;
    }

    @DeleteMapping(value = "/media/delete/{id}")
    public ResponseEntity deleteMedia(@PathVariable("id") String id) {
        boolean response = service.deleteMedia(id);
        if (response)
            return new ResponseEntity<>("media deleted successfully!", HttpStatus.OK);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/media/update/{id}")
    public ResponseEntity updateMedia(@PathVariable("id") String id, @RequestParam("score") int score,
                                      @RequestParam("name") String name, @RequestParam("genre") String genre,
                                      @RequestParam("length") String length, @RequestParam("publishDate") String publishDate) {
        Media media = service.getMedia(id);
        if (media != null) {
            media.setName(name);
            media.setGenre(genre);
            media.setLength(length);
            media.setPublishDate(publishDate);
            media.setScore(score);
            service.addOrUpdateMedia(media);
            return new ResponseEntity(media, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/testtt/{p}")
    public ModelAndView g(HttpServletRequest request, @PathVariable("p") String p) {
//        ModelAndView modelAndView = new ModelAndView();
//        Artist artist = service.getArtist("610d9282f8253b55cc3fdbb8");
////        Media media = service.g
//        modelAndView.addObject("artist", artist);
//        modelAndView.setViewName("test");

        HttpSession session = request.getSession();

        ModelAndView model = new ModelAndView();
        Artist artist = service.getArtist(p);

        List<AlbumArtist> albumArtists = service.getByArtist(artist);
        List<Album> albums = new ArrayList<>();
        for (int i = 0; i < albumArtists.size(); i++)
            albums.add(albumArtists.get(i).getAlbum());
        System.out.println(artist);

        model.addObject("artist", artist);
        model.addObject("albums", albums);
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("test");

        return model;
    }

}
