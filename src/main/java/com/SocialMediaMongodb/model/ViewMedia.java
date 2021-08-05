package com.SocialMediaMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ViewMedia {
    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Media media;

    public ViewMedia() {
    }

    public ViewMedia(User user, Media media) {
        this.user = user;
        this.media = media;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "ViewMedia{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", media=" + media +
                '}';
    }
}
