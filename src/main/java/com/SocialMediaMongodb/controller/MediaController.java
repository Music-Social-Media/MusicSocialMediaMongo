package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.Album;
import com.SocialMediaMongodb.model.Artist;
import com.SocialMediaMongodb.model.Media;
import com.SocialMediaMongodb.model.User;
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
import java.util.Date;
import java.util.List;

@Controller
public class MediaController {
    private static final String BASE_DIR = "src/main/resources/static/music-files/";

    @Autowired
    private SocialMediaService service;


    @RequestMapping("/media/getAll")
    public ModelAndView getAllMedia() {
        ModelAndView model = new ModelAndView();
        List<Media> medias = service.getAllMedia();

        model.addObject("medias", medias);
        model.setViewName("medias");
        return model;
    }

    @GetMapping("/media/{id}")
    public ModelAndView getMedia(HttpServletRequest request, @PathVariable String id) {
        HttpSession session = request.getSession();
        int viewCount = setView((String) session.getAttribute("userID"), id);

        ModelAndView model = new ModelAndView();
        Media media = service.getMedia(id);
        System.out.println(media.toString());

        model.addObject("media", media);
        model.addObject("viewCount", viewCount);
        model.setViewName("media");

        return model;
    }

    @GetMapping("/media/get/{id}")
    public ResponseEntity getMediaRest(@PathVariable String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int viewCount = setView((String) session.getAttribute("userID"), id);

        Media media = service.getMedia(id);
        System.out.println("media: " + media.toString());
        if (media == null)
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/json"))
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(media);
    }

    public int setView(String userID, String mediaID) {
        User user = service.getUser(userID);
        Media media = service.getMedia(mediaID);

        List view = user.getViewMedia();
//        view.add(media);
//        user.setViewMedia(media);
        service.updateUser(user);

        return view.size();
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
//                .contentType(MediaType.parseMediaType("application/json"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + mediaName + "\"")
                .contentLength(resource.contentLength())
                .cacheControl(CacheControl.noCache().mustRevalidate())
                .body(resource);
    }

    @PostMapping("/media/upload")
    public ResponseEntity addMedia(@RequestParam("file") MultipartFile file, @RequestParam("albumName") String albumName,
                                   @RequestParam("mediaName") String mediaName, @RequestParam("genre") String genre
            , @RequestParam("artistID") String artistID) {

        if (file.isEmpty())
            return (ResponseEntity) ResponseEntity.noContent();

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(BASE_DIR + file.getOriginalFilename());
            Files.write(path, file.getBytes());

            Album album = service.getAlbumByName(albumName);
            Artist artist = service.getArtist(artistID);
            if (album == null && artist == null) {
                System.out.println("The required media or artist is not exists !!");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

            Media uploadMedia = new Media();
            uploadMedia.setName(mediaName);
            uploadMedia.setLength((int) file.getSize());
            uploadMedia.setGenre(genre);
            uploadMedia.setPath(file.getOriginalFilename());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            uploadMedia.setPublishDate(formatter.format(date));


            if (artist != null)
                album.setCompiles(artist);


            service.addOrUpdateMedia(uploadMedia);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/media/delete/{id}")
    public ResponseEntity deleteMedia(@PathVariable("id") String id) {
        boolean response = service.deleteMedia(id);
        if (response)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping("/media/update/{id}")
    public ResponseEntity updateMedia(@PathVariable("id") String id, @RequestParam("score") int score,
                                      @RequestParam("name") String name, @RequestParam("genre") String genre,
                                      @RequestParam("length") int length, @RequestParam("publishDate") String publishDate) {
        Media media = service.getMedia(id);
        if (media != null) {
            media.setName(name);
            media.setGenre(genre);
            media.setLength(length);
            media.setPublishDate(publishDate);
            media.setScore(score);
            service.addOrUpdateMedia(media);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
