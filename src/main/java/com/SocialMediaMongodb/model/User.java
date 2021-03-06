package com.SocialMediaMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class User {
    @Id
    private String userID;
    private String firstname;
    private String lastname;
    private String picture;
    private String biography;
    private String username;
    private String password;
    private String email;

//    @DBRef
//    private List<Media> likeMedia;
//
//    @DBRef
//    private List<Media> viewMedia;
//
//    @DBRef
//    private List<Artist> followArtist;


    public User() {
    }

    public User(String firstname, String lastname, String picture, String biography, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.picture = picture;
        this.biography = biography;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Media> getLikeMedia() {
//        return likeMedia;
//    }
//
//    public void setLikeMedia(List<Media> likeMedia) {
//        this.likeMedia = likeMedia;
//    }
//
//    public List<Media> getViewMedia() {
//        return viewMedia;
//    }
//
//    public void setViewMedia(Media media) {
//        if (!viewMedia.contains(media))
//            viewMedia.add(media);
//    }
//
//    public List<Artist> getFollowArtist() {
//        return followArtist;
//    }

//    public void setFollowArtist(Artist artist) {
//        if (!followArtist.contains(artist))
//            followArtist.add(artist);
//    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", picture='" + picture + '\'' +
                ", biography='" + biography + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
