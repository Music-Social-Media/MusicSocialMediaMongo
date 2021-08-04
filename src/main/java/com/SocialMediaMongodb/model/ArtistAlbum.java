package com.SocialMediaMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class ArtistAlbum {
    @Id
    private String id;

    //    @DBRef
//    private List<Artist> artists = new ArrayList<>();
//
//    @DBRef
//    private List<Album> albums = new ArrayList<>();
    @DBRef
    private Artist artist;

    @DBRef
    private Album album;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public List<Artist> getArtists() {
//        return artists;
//    }
//
//    public void setArtists(Artist artist) {
//        if (!artists.contains(artist))
//            artists.add(artist);
//    }
//
//    public List<Album> getAlbums() {
//        return albums;
//    }
//
//    public void setAlbums(Album album) {
//        if (!albums.contains(album))
//            albums.add(album);
//    }


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "ArtistAlbum{" +
                "id='" + id + '\'' +
                ", artist=" + artist +
                ", album=" + album +
                '}';
    }
}
