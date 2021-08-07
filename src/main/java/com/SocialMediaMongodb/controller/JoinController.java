package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.*;
import com.SocialMediaMongodb.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JoinController {
    @Autowired
    private SocialMediaService service;

    @GetMapping("follow/getAll")
    public ResponseEntity getAllFollows() {
        return new ResponseEntity(service.getAllFollows(), HttpStatus.OK);
    }

    @RequestMapping("/follow/{id}")
    public String follow(@PathVariable("id") String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        if (userID == null)
            return "login";
        User user = service.getUser(userID);
        Artist artist = service.getArtist(id);
        service.checkDuplicatedFollows(user, artist);

        return "index";
    }

    @PostMapping(value = "/rest/follow/{id}")
    public ResponseEntity followRest(@PathVariable("id") String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));
        Artist artist = service.getArtist(id);

        boolean response = service.checkDuplicatedFollows(user, artist);
        if (response)
            return new ResponseEntity(response, HttpStatus.OK);
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }


    @RequestMapping("/like/{id}")
    public ModelAndView like(HttpServletRequest request, @PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        if (userID != null) {
            User user = service.getUser(userID);

            service.checkDuplicatedLikes(user, service.getMedia(id));
            model.addObject("userName", session.getAttribute("userName"));
            model.setViewName("likedMedia");
        } else
            model.setViewName("login");
        return model;
    }

    @PostMapping("/rest/like/{id}")
    public ResponseEntity likeRest(HttpServletRequest request, @PathVariable("id") String id) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));

        boolean response = service.checkDuplicatedLikes(user, service.getMedia(id));
        if (response)
            return new ResponseEntity(response, HttpStatus.OK);
        return new ResponseEntity(response, HttpStatus.CONFLICT);

    }

    @GetMapping("/likedMedia")
    public ModelAndView getLikedMedia(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            User user = service.getUser(userID);
            List<Media> medias = new ArrayList<>();
            List<LikedMedia> likes = service.getLikedMedias(user);
            int start = 0, end = likes.size();
            if (likes.size() > 10)
                start = likes.size() - 10;

            for (int i = start; i < end; i++)
                medias.add(likes.get(i).getMedia());

            model.addObject("medias", medias);
            model.addObject("userName", user.getUsername());
            model.setViewName("likedMedia");
        } else model.setViewName("login");
        return model;
    }

    @GetMapping("/rest/likedMedia")
    public ResponseEntity getLikedMediaRest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            User user = service.getUser(userID);
            List<Media> medias = new ArrayList<>();
            List<LikedMedia> likes = service.getLikedMedias(user);
            int start = 0, end = likes.size();
            if (likes.size() > 10)
                start = likes.size() - 10;

            for (int i = start; i < end; i++)
                medias.add(likes.get(i).getMedia());
            return new ResponseEntity(medias, HttpStatus.OK);
        }
        return new ResponseEntity("you should login!", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @GetMapping("likes/getAll")
    public ResponseEntity getAllLikes() {
        return new ResponseEntity(service.getAllLikedMedia(), HttpStatus.OK);
    }

    @GetMapping("/rest/userLikes/{id}")
    public ResponseEntity getUserLikesRest(HttpServletRequest request, @PathVariable("id") String id) {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            Media media = service.getMedia(id);

            List<User> users = new ArrayList<>();
            List<LikedMedia> likes = service.getUserLikes(media);
            for (int i = 0; i < likes.size(); i++)
                users.add(likes.get(i).getUser());

            return new ResponseEntity(users, HttpStatus.OK);
        }
        return new ResponseEntity("you should login!", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }

    @GetMapping("/userLikes/{id}")
    public ModelAndView getUserLikes(HttpServletRequest request, @PathVariable("id") String id) {
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            Media media = service.getMedia(id);

            List<User> users = new ArrayList<>();
            List<LikedMedia> likes = service.getUserLikes(media);
            for (int i = 0; i < likes.size(); i++)
                users.add(likes.get(i).getUser());

            model.setViewName("");
        } else
            model.setViewName("login");
        return model;
    }

    @GetMapping("view/getAll")
    public ResponseEntity getAllViews() {
        return new ResponseEntity(service.getAllViewedMedia(), HttpStatus.OK);
    }

    @GetMapping("albumArtists/getAll")
    public ResponseEntity getAllAlbumArtists() {
        return new ResponseEntity(service.getAllAlbumArtist(), HttpStatus.OK);
    }

}
