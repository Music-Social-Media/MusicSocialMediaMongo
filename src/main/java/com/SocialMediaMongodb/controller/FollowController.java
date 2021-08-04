package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.Artist;
import com.SocialMediaMongodb.model.FollowArtist;
import com.SocialMediaMongodb.model.User;
import com.SocialMediaMongodb.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FollowController {
    @Autowired
    private SocialMediaService service;

    @GetMapping("follow/getAll")
    public ResponseEntity getAllFollows() {
        return new ResponseEntity(service.getAllFollows(), HttpStatus.OK);
    }

    @PostMapping(value = "/follow/{id}")
    public ResponseEntity follow(@PathVariable("id") String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));
        Artist artist = service.getArtist(id);

        boolean response = service.checkDuplicatedFollows(user, artist);
        if (response)
            return new ResponseEntity(response, HttpStatus.OK);
        return new ResponseEntity(response, HttpStatus.FOUND);
    }
}
