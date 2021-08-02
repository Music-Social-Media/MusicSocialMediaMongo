package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.Artist;
import com.SocialMediaMongodb.service.ArtistService;
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
import java.util.List;

@Controller
public class ArtistController {

    private static final String ALBUM_IMG_DIR = "src/main/resources/uploads/";

    @Autowired
    private ArtistService artistService;

    @PostMapping("/artist/add")
    public ResponseEntity addArtist(@RequestParam("file") MultipartFile file,
                                    @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                    @RequestParam("biography") String biography, @RequestParam("birthDate") String birthDate) throws IOException {
        Path path = Paths.get(ALBUM_IMG_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        if (artistService.findArtistNOTDuplicate(firstName, lastName, birthDate)) {
            Artist artist = new Artist(firstName, lastName, ALBUM_IMG_DIR + file.getOriginalFilename(), biography, birthDate);
            artistService.addOrUpdateArtist(artist);
            return new ResponseEntity<>(artist, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/artist/update/{id}")
    public ResponseEntity UpdateArtist(@PathVariable("id") String id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                       @RequestParam("biography") String biography, @RequestParam("birthDate") String birthDate) {
        Artist artist = artistService.getArtist(id);
        artist.setFirstname(firstName);
        artist.setLastname(lastName);
        artist.setBiography(biography);
        artist.setBirthdate(birthDate);
        boolean response = artistService.addOrUpdateArtist(artist);
        if (response)
            return new ResponseEntity<>(artist, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/artist/delete/{id}")
    public ResponseEntity deleteArtist(@PathVariable("id") String id) {
        boolean response = artistService.deleteArtist(id);
        if (response)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/artists")
    public @ResponseBody
    ModelAndView getAllArtists() {
        ModelAndView model = new ModelAndView();
        List<Artist> artists = artistService.getAllArtist();

        model.addObject("artists", artists);
        model.setViewName("artists");
        return model;
    }

    @GetMapping(path = "/artist/{id}")
    public ModelAndView getArtist(@PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        Artist artist = artistService.getArtist(id);

        model.addObject("artist", artist);
        model.setViewName("artist");

        return model;
    }

}
