package com.SocialMediaMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Album {
    @Id
    private String albumID;
    private String name;
    private String publishDate;
    private int score;
    private String genre;
    private String picture;


    public Album() {
    }


    @DBRef
    private List<Artist> compiles = new ArrayList<>();

    @DBRef
    private List<Media> contain = new ArrayList<>();


    public Album(String name, String publishDate, int score, String genre, String picture) {
        this.name = name;
        this.publishDate = publishDate;
        this.score = score;
        this.genre = genre;
        this.picture = picture;
    }

    public String getAlbumID() {
        return albumID;
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

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Artist> getCompiles() {
        return compiles;
    }

    public void setCompiles(Artist artist) {
        if (!compiles.contains(artist))
            compiles.add(artist);
    }

    public List<Media> getContain() {
        return contain;
    }


    public void setContain(Media media) {
        if (!contain.contains(media))
            contain.add(media);
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumID=" + albumID +
                ", name='" + name + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", score=" + score +
                ", genre='" + genre + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}






