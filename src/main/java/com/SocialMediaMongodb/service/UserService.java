//package com.SocialMediaMongodb.service;
//
//import com.SocialMediaMongodb.model.User;
//import com.SocialMediaMongodb.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    public User getUser(String id) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isPresent())
//            return user.get();
//        else
//            return null;
//    }
//
//    public List<User> getAllUser() {
//        List<User> users = userRepository.findAll();
//        return users;
//    }
//
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//
//    public void addUser(User resource) {
//        userRepository.save(resource);
//    }
//
////    public boolean deleteUser(String id) {
////        Optional<User> user = userRepository.findById(id);
////        if (user.isPresent()) {
////            userRepository.deleteById(id);
////            return true;
////        } else
////            return false;
////    }
//
//    public void updateUser(User user) {
//        if (user.getUserID() == null)
//            userRepository.save(user);
//        else {
//            Optional<User> existingUser = userRepository.findById(user.getUserID());
//            if (existingUser.isPresent()) {
//                User newUser = existingUser.get();
//                newUser.setFirstname(user.getFirstname());
//                newUser.setLastname(user.getLastname());
//                newUser.setPassword(user.getPassword());
//                userRepository.save(newUser);
//            }
//        }
//    }
//}
