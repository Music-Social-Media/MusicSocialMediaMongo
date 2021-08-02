package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.Album;
import com.SocialMediaMongodb.model.Media;
import com.SocialMediaMongodb.service.AlbumService;
import com.SocialMediaMongodb.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AlbumController {
    private static final String ALBUM_IMG_DIR = "src/main/resources/uploads/";

    @Autowired
    private AlbumService albumService;
    private MediaService mediaService;

    @PostMapping("/album/add")
    public ResponseEntity addAlbum(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("genre") String genre) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Path path = Paths.get(ALBUM_IMG_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        Album album = new Album(name, formatter.format(date), 0, genre, ALBUM_IMG_DIR + file.getOriginalFilename());
        albumService.addOrUpdateAlbum(album);

        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @DeleteMapping("/album/delete/{id}")
    public ResponseEntity deleteAlbum(@PathVariable("id") String id) {
        boolean flag = albumService.deleteAlbum(id);
        if (flag)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("album/update/{id}")
    public ResponseEntity updateAlbum(@PathVariable("id") String id, @RequestParam("albumName") String albumName,
                                      @RequestParam("genre") String genre, @RequestParam("score") int score,
                                      @RequestParam("publishDate") String publishDate) {
        Album album = albumService.getAlbum(id);
        if (album != null) {
            album.setName(albumName);
            album.setGenre(genre);
            album.setScore(score);
            album.setPublishDate(publishDate);
            albumService.addOrUpdateAlbum(album);
            return new ResponseEntity(album, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/album/get/{id}")
    public ModelAndView getAlbum(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        Album album = albumService.getAlbum(id);
        System.out.println("---" + album.toString());
        System.out.println("id:::" + id);

        List<Media> media = mediaService.getMediaByAlbumID(id);
        System.out.println(media.toString());
        model.addObject("album", album);
        if (media.size() > 0)
            model.addObject("media", media.get(0));
        model.addObject("medias", media);
        model.setViewName("album");

        return model;
    }

    @GetMapping("/albums")
    public ModelAndView getAllAlbum() {
        ModelAndView model = new ModelAndView();
        List<Album> albums = albumService.getAllAlbum();

        model.addObject("albums", albums);
        model.setViewName("albums");
        return model;
    }
}
