package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.Album;
import com.SocialMediaMongodb.model.AlbumArtist;
import com.SocialMediaMongodb.model.Artist;
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
import java.util.List;

@Controller
public class ArtistController {

    private static final String ALBUM_IMG_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private SocialMediaService service;

    @PostMapping("/artist/add")
    public ResponseEntity addArtist(@RequestParam("file") MultipartFile file,
                                    @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                    @RequestParam("biography") String biography, @RequestParam("birthDate") String birthDate) throws IOException {
        Path path = Paths.get(ALBUM_IMG_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        if (service.findArtistNOTDuplicate(firstName, lastName, birthDate)) {
            Artist artist = new Artist(firstName, lastName, "uploads/" + file.getOriginalFilename(), biography, birthDate);
            service.addOrUpdateArtist(artist);
            return new ResponseEntity<>(artist, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/artist/update/{id}")
    public ResponseEntity UpdateArtist(@PathVariable("id") String id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                       @RequestParam("biography") String biography, @RequestParam("birthDate") String birthDate) {
        Artist artist = service.getArtist(id);
        artist.setFirstname(firstName);
        artist.setLastname(lastName);
        artist.setBiography(biography);
        artist.setBirthdate(birthDate);
        boolean response = service.addOrUpdateArtist(artist);
        if (response)
            return new ResponseEntity<>(artist, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/artist/delete/{id}")
    public ResponseEntity deleteArtist(@PathVariable("id") String id) {
        boolean response = service.deleteArtist(id);
        if (response)
            return new ResponseEntity<>("artist deleted successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/artists")
    public @ResponseBody
    ModelAndView getAllArtists(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ModelAndView model = new ModelAndView();
        List<Artist> artists = service.getAllArtist();

        model.addObject("artists", artists);
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("artists");
        return model;
    }

    @GetMapping(path = "/rest/artists")
    public ResponseEntity getAllArtistsRest() {
        List<Artist> artists = service.getAllArtist();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping(path = "/artist/get/{id}")
    public ModelAndView getArtist(@PathVariable("id") String id, HttpServletRequest request) {
        HttpSession session = request.getSession();

        ModelAndView model = new ModelAndView();
        Artist artist = service.getArtist(id);

//        List<Album> albums = service.getAlbumByArtist(artist.getArtistID());
        List<AlbumArtist> albums = service.getByArtist(artist);
        System.out.println("oooo" + albums);
        model.addObject("artist", artist);
        model.addObject("albums", albums);
        model.addObject("userName", session.getAttribute("userName"));
        model.setViewName("artist");

        return model;
    }

    @GetMapping(path = "/rest/artist/get/{id}")
    public ResponseEntity getArtistRest(@PathVariable("id") String id) {
        Artist artist = service.getArtist(id);
        if (artist != null)
            return new ResponseEntity<>(artist, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
