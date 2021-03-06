package com.SocialMediaMongodb.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
public class Artist {
    @Id
    private String artistID;
    private String firstname;
    private String lastname;
    private String picture;
    private String biography;
    private String birthdate;

//    @DBRef
//    private List<User> follows;
//
//
//    @DBRef
//    private List<Album> albumsOfArtist;


    public Artist() {

    }

    public Artist(String firstname, String lastname, String picture, String biography, String birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.picture = picture;
        this.biography = biography;
        this.birthdate = birthdate;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

//    public List<User> getFollows() {
//        return follows;
//    }
//
//    public void setFollowAlbum(User user) {
//        if (!follows.contains(user))
//            follows.add(user);
//    }
//
//    public List<Album> getAlbumsOfArtist() {
//        return albumsOfArtist;
//    }
//
//    public void setAlbumsOfArtist(Album album) {
//        if (!albumsOfArtist.contains(album))
//            albumsOfArtist.add(album);
//    }


    @Override
    public String toString() {
        return "artist{" +
                "artistID=" + artistID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", picture='" + picture + '\'' +
                ", biography='" + biography + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
