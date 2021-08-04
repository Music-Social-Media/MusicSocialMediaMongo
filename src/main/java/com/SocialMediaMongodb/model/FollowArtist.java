package com.SocialMediaMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FollowArtist {
    @Id
    private String followID;
    @DBRef
    private User user;
    @DBRef
    private Artist artist;

    public FollowArtist() {
    }

    public FollowArtist(User user, Artist artist) {
        this.user = user;
        this.artist = artist;
    }

    public String getFollowID() {
        return followID;
    }

    public void setFollowID(String followID) {
        this.followID = followID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
