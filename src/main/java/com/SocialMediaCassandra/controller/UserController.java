package com.SocialMediaCassandra.controller;

import com.SocialMediaCassandra.model.User;
import com.SocialMediaCassandra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("heeelllooo");
        List<User> users = userService.getAllUsers();
        System.out.println(users.toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/post")
    public ResponseEntity<User> createTutorial() {
        System.out.println("posttt");

        User user = new User("zahra2", "12345", "zahra2@gmail.com");
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
