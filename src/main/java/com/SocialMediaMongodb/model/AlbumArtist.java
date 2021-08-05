package com.SocialMediaMongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AlbumArtist {
    @Id
    private String id;
    @DBRef
    private Album album;
    @DBRef
    private Artist artist;

    public AlbumArtist() {
    }

    public AlbumArtist(Album album, Artist artist) {
        this.album = album;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "AlbumArtist{" +
                "id='" + id + '\'' +
                ", album=" + album +
                ", artist=" + artist +
                '}';
    }
}
