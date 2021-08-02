package com.SocialMediaMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collation = "album")
public class Album {
    @Id
    private String albumID;
    private String name;
    private String publishDate;
    private int score;
    private String genre;
    private String picture;

    //    private List media;
//    private Artist artist;

    public Album() {
    }

//    public Album(String name, String publishDate, int score, String genre, String picture) {
//        this.name = name;
//        this.publishDate = publishDate;
//        this.score = score;
//        this.genre = genre;
//        this.picture = picture;
////        this.media = media;
////        this.artist = artist;
//    }

//    public List getMedia() {
//        return media;
//    }
//
//    public void setMedia(List media) {
//        this.media = media;
//    }

//    public Artist getArtist() {
//        return artist;
//    }
//
//    public void setArtist(Artist artist) {
//        this.artist = artist;
//    }

    //    @DBRef
//    private List<Artist> compiles = new ArrayList<>();
//
//    @DBRef
//    private List<Media> contain = new ArrayList<>();
//
//    public Album() {
//
//    }

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

//    public List<Artist> getCompiles() {
//        return compiles;
//    }
//
//    public void setCompiles(List<Artist> compiles) {
//        this.compiles = compiles;
//    }
//
//    public List<Media> getContain() {
//        return contain;
//    }
//
//    public void setContain(List<Media> contain) {
//        this.contain = contain;
//    }

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






