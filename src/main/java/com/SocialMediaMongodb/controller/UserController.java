package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.User;
import com.SocialMediaMongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        User user = new User("zahra", "1234");
        userRepository.insert(user);

//        userRepository.save(new User("zahra", "1234"));
//        for (User user : userRepository.findAll())
//            System.out.println(user);
        return "index";
    }
}
