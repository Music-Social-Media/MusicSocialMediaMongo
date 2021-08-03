package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.User;
import com.SocialMediaMongodb.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private SocialMediaService service;

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = null, userID = (String) session.getAttribute("userID");
        if (userID != null) {
            User user = service.getUser(userID);
            userName = user.getUsername();
        }

        ModelAndView model = new ModelAndView();
        model.addObject("userName", userName);
        model.setViewName("index");
        return model;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @RequestMapping(value = "/user/signup")
    public String registerUser(HttpServletRequest request, @RequestParam("username") String username,
                               @RequestParam("email") String email, @RequestParam("password") String password) {
        User isExitUser = service.getUserByEmail(email);
        if (isExitUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUsername(username);
            service.addOrUpdateUser(newUser);

            HttpSession session = request.getSession();
            session.setAttribute("userID", service.getUserByEmail(email).getUserID());

            return "index";
        } else {
            System.out.println("User Already exists!");
            return "signup";
        }
    }

    @PostMapping(value = "/rest/user/signup")
    public ResponseEntity registerUserRest(HttpServletRequest request, @RequestParam("username") String username,
                                           @RequestParam("email") String email, @RequestParam("password") String password) {
        User isExitUser = service.getUserByEmail(email);
        if (isExitUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUsername(username);
            service.addOrUpdateUser(newUser);

            HttpSession session = request.getSession();
            session.setAttribute("userID", service.getUserByEmail(email).getUserID());

            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } else {
            System.out.println("User Already exists!");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/login")
    public ModelAndView loginUser(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        User isExitUser = service.getUserByEmail(request.getParameter("email"));

        if (isExitUser != null && isExitUser.getPassword().equals(request.getParameter("password"))) {
            model.addObject("userName", isExitUser.getUsername());
            model.setViewName("index");
            return model;
        }
        model.setViewName("login");
        return model;

    }

    @PostMapping(value = "/rest/user/login")
    public ResponseEntity loginUserRest(HttpServletRequest request) {
        User isExitUser = service.getUserByEmail(request.getParameter("email"));
        if (isExitUser != null && isExitUser.getPassword().equals(request.getParameter("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute("userID", isExitUser.getUserID());
            return new ResponseEntity<>(isExitUser, HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/profile")
    public ModelAndView profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));

        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        model.setViewName("profile");

        return model;
    }

    @GetMapping("/rest/profile")
    public ResponseEntity profileRest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/changePassword")
    public ModelAndView userEditPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));

        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        model.setViewName("changePassword");
        return model;
    }

    @RequestMapping("/user/edit")
    public String updateUser(@RequestParam("id") String id, @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName, @RequestParam("biography") String biography) {
        User user = service.getUser(id);
        if (user != null) {
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setBiography(biography);
            service.addOrUpdateUser(user);
        }
        return "profile";
    }

    @PutMapping("/rest/user/edit")
    public ResponseEntity updateUserRest(@RequestParam("id") String id, @RequestParam("firstName") String firstName,
                                         @RequestParam("lastName") String lastName, @RequestParam("biography") String biography) {
        User user = service.getUser(id);
        if (user != null) {
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setBiography(biography);
            service.addOrUpdateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @RequestMapping("/changePassword")
    public String changePassword(HttpServletRequest request, @RequestParam("currentPassword") String currentPassword, @RequestParam("password") String password,
                                 @RequestParam("repeatNewPassword") String repeatNewPassword) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));
        System.out.println(user.toString());
        if (user != null && user.getPassword().equals(currentPassword) && password.equals(repeatNewPassword)) {
            user.setPassword(password);
            service.addOrUpdateUser(user);
        }
        return "changePassword";
    }

    @PutMapping("/rest/changePassword")
    public ResponseEntity changePasswordRest(HttpServletRequest request, @RequestParam("currentPassword") String currentPassword, @RequestParam("password") String password,
                                             @RequestParam("repeatNewPassword") String repeatNewPassword) {
        HttpSession session = request.getSession();
        User user = service.getUser((String) session.getAttribute("userID"));

        if (user != null && user.getPassword().equals(currentPassword) && password.equals(repeatNewPassword)) {
            user.setPassword(password);
            service.addOrUpdateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/getAll")
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        boolean response = service.deleteUser(id);
        if (response)
            return new ResponseEntity<>("user deleted successfully!", HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
}
