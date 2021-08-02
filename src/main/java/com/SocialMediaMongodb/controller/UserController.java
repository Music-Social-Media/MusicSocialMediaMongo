package com.SocialMediaMongodb.controller;

import com.SocialMediaMongodb.model.User;
import com.SocialMediaMongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @GetMapping("/add")
    public String add() {
        User user = new User("zahra", "alizadeh", null, "bio", "zahra12", "1234", "zahra@gmail.com");
        userService.addUser(user);
        return "index";
    }

    @RequestMapping(value = "/user/signup")
    public String registerUser(HttpServletRequest request, @RequestParam("username") String username,
                               @RequestParam("email") String email, @RequestParam("password") String password) {
        User isExitUser = userService.getUserByEmail(email);
        System.out.println("iiiiii" + isExitUser.toString());
        if (isExitUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUsername(username);
            userService.addUser(newUser);

            HttpSession session = request.getSession();
            session.setAttribute("userID", userService.getUserByEmail(email).getUserID());

            return "index";
        } else {
            System.out.println("User Already exists!");
            return "signup";
        }
    }

    @RequestMapping(value = "/user/login")
    public String loginUser(HttpServletRequest request) {
        User isExitUser = userService.getUserByEmail(request.getParameter("email"));
        if (isExitUser != null && isExitUser.getPassword().equals(request.getParameter("password"))) {
            HttpSession session = request.getSession();
            session.setAttribute("userID", isExitUser.getUserID());
            return "index";
        } else
            return "login";
    }


    @GetMapping("/profile")
    public ModelAndView profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("userID"));
        User user = userService.getUser((String) session.getAttribute("userID"));
        System.out.println(user.toString());

        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        model.setViewName("profile");

        return model;
    }

    @GetMapping("/user/changePassword")
    public ModelAndView userEditPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userService.getUser((String) session.getAttribute("userID"));

        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        model.setViewName("changePassword");
        return model;
    }

    @RequestMapping("/user/edit")
    public String updateUser(@RequestParam("id") String id, @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName) {
        User user = userService.getUser(id);
        if (user != null) {
            user.setFirstname(firstName);
            user.setLastname(lastName);
//            user.setBiography(biography);
            userService.updateUser(user);
        }
        return "profile";
    }

    @RequestMapping("/changePassword")
    public String changePassword(HttpServletRequest request, @RequestParam("currentPassword") String currentPassword, @RequestParam("password") String password,
                                 @RequestParam("repeatNewPassword") String repeatNewPassword) {
        HttpSession session = request.getSession();
        User user = userService.getUser((String) session.getAttribute("userID"));
        System.out.println(user.toString());
        if (user != null && user.getPassword().equals(currentPassword) && password.equals(repeatNewPassword)) {
            user.setPassword(password);
            userService.updateUser(user);
        }
        return "changePassword";
    }

    @RequestMapping("/user/getAll")
    public String getAllUsers() {
        System.out.println(userService.getAllUser());
        return "index";
    }
}
