package com.SocialMediaMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Media {
    @Id
    private String mediaID;
    private String name;
    private int score;
    private String genre;
    private int length;
    private String publishDate;
    private String path;


    @DBRef
    private List<User> likes = new ArrayList<>();

    @DBRef
    private List<User> views = new ArrayList<>();

    @DBRef
    private Album album = new Album();

    public Media() {

    }

    public Media(String name, int score, String genre, int length, String publishDate, String path) {
        this.name = name;
        this.score = score;
        this.genre = genre;
        this.length = length;
        this.publishDate = publishDate;
        this.path = path;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public List<User> getLikes() {
        return likes;
    }

    public List<User> getViews() {
        return views;
    }

    public void setLikes(User user) {
        if (!likes.contains(user))
            likes.add(user);
    }


    public void setViews(User user) {
        if (!views.contains(user))
            views.add(user);
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mediaID='" + mediaID + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", genre='" + genre + '\'' +
                ", length=" + length +
                ", publishDate='" + publishDate + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
