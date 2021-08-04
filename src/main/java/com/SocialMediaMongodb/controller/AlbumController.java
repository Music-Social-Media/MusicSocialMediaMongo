package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.Album;
import com.SocialMediaMongodb.model.Artist;
import com.SocialMediaMongodb.model.Media;
import com.SocialMediaMongodb.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AlbumController {
    private static final String ALBUM_IMG_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private SocialMediaService service;


    @PostMapping("/album/add")
    public ResponseEntity addAlbum(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                                   @RequestParam("genre") String genre, @RequestParam("artisID") String artisID) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Path path = Paths.get(ALBUM_IMG_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        Album album = new Album(name, formatter.format(date), 0, genre, "uploads/" + file.getOriginalFilename());

        Artist artist = service.getArtist(artisID);
        album.setArtist(artist);

        service.addOrUpdateAlbum(album);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @DeleteMapping("/album/delete/{id}")
    public ResponseEntity deleteAlbum(@PathVariable("id") String id) {
        boolean flag = service.deleteAlbum(id);
        if (flag)
            return new ResponseEntity<>("album deleted successfully!", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("album/update/{id}")
    public ResponseEntity updateAlbum(@PathVariable("id") String id, @RequestParam("albumName") String albumName,
                                      @RequestParam("genre") String genre, @RequestParam("score") int score,
                                      @RequestParam("publishDate") String publishDate) {
        Album album = service.getAlbum(id);
        if (album != null) {
            album.setName(albumName);
            album.setGenre(genre);
            album.setScore(score);
            album.setPublishDate(publishDate);
            service.addOrUpdateAlbum(album);
            return new ResponseEntity(album, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/album/get/{id}")
    public ModelAndView getAlbum(@PathVariable("id") String id, HttpServletRequest request) {
        HttpSession session = request.getSession();

        ModelAndView model = new ModelAndView();
        Album album = service.getAlbum(id);

        List<Media> medias = service.getMediaByAlbumID(id);
        if (medias.size() > 0)
            model.addObject("media", medias.get(0));
        model.addObject("medias", medias);
        model.addObject("album", album);
        model.addObject("artist", album.getArtist());
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("album");

        System.out.println(album.getArtist().toString());
        System.out.println(medias.get(0).toString());

        return model;
    }

    @GetMapping("/rest/album/get/{id}")
    public ResponseEntity getAlbumRest(@PathVariable("id") String id) {
        Album album = service.getAlbum(id);
        return new ResponseEntity(album, HttpStatus.OK);
    }

    @GetMapping("/albums")
    public ModelAndView getAllAlbum(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ModelAndView model = new ModelAndView();
        List<Album> albums = service.getAllAlbum();

        model.addObject("albums", albums);
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("albums");
        return model;
    }

    @GetMapping("/rest/albums")
    public ResponseEntity getAllAlbumRest() {
        return new ResponseEntity(service.getAllAlbum(), HttpStatus.OK);
    }
}
