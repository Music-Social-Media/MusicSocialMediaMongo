package com.SocialMediaMongodb.service;

import com.SocialMediaMongodb.model.*;
import com.SocialMediaMongodb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocialMediaService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private LikedMediaRepository likedMediaRepository;
    @Autowired
    private ViewMediaRepository viewMediaRepository;
    @Autowired
    private AlbumArtistRepository albumArtistRepository;

    // *******************************User******************************* //

    public User getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            return null;
    }

    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public void addOrUpdateUser(User user) {
        userRepository.save(user);
    }

    public boolean deleteUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    // *******************************Artist******************************* //

    public List<Artist> getAllArtist() {
        return artistRepository.findAll();

    }

    public Artist getArtist(String id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent())
            return artist.get();
        else
            return null;

    }

    public boolean addOrUpdateArtist(Artist artist) {
        artistRepository.save(artist);
        return true;
    }

    public boolean deleteArtist(String id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent()) {
            artistRepository.deleteById(id);
            return true;
        } else
            return false;
    }

    public boolean findArtistNOTDuplicate(String firstname, String lastname, String birthdate) {
        List<Artist> artists = artistRepository.findByFirstnameAndLastnameAndBirthdate(firstname, lastname, birthdate);

        if (artists.size() == 0)
            return true;
        else
            return false;
    }

//    public Artist getArtistByName(String firstName, String lastName) {
//        return artistRepository.findByName(firstName, lastName);
//    }
    // *******************************Album******************************* //

    public List<Album> getAllAlbum() {
        return albumRepository.findAll();
    }

    public Album getAlbum(String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent())
            return album.get();
        else
            return null;

    }

    public Album getAlbumByName(String name) {
        return albumRepository.findByName(name);
    }

    public void addOrUpdateAlbum(Album album) {
        albumRepository.save(album);
    }

    public boolean deleteAlbum(String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isPresent()) {
            albumRepository.deleteById(id);
            return true;
        } else
            return false;
    }

//    public List<Album> getAlbumByArtistID(String id) {
//        return albumRepository.findByArtist(id);
//    }

    // *******************************Media******************************* //

    public List<Media> getAllMedia() {
        return mediaRepository.findAll(Sort.by(Sort.Direction.DESC, "publishDate"));
    }

    public Media getMedia(String id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isPresent())
            return media.get();
        else
            return null;
    }

    public List<Media> getMediaByAlbumID(String id) {
        return mediaRepository.findByAlbum(id);
    }

    public void addOrUpdateMedia(Media media) {
        mediaRepository.save(media);
    }

    public boolean deleteMedia(String id) {
        Optional<Media> resource = mediaRepository.findById(id);
        if (resource.isPresent()) {
            mediaRepository.deleteById(id);
            return true;
        } else
            return false;
    }
    // *******************************Follow Artist******************************* //

    public List<FollowArtist> getAllFollows() {
        return followRepository.findAll();
    }

    public void addFollow(FollowArtist follow) {
        followRepository.save(follow);
    }

    public boolean checkDuplicatedFollows(User user, Artist artist) {
        FollowArtist follows = followRepository.findByUserAndArtist(user, artist);
        if (follows == null) {
            followRepository.save(new FollowArtist(user, artist));
            return true;
        }
        followRepository.deleteById(follows.getFollowID());
        return false;
    }

    // *******************************Like Media******************************* //

    public List<LikedMedia> getAllLikedMedia() {
        return likedMediaRepository.findAll();
    }

    public void addLike(LikedMedia likedMedia) {
        likedMediaRepository.save(likedMedia);
    }

    public boolean checkDuplicatedLikes(User user, Media media) {
        LikedMedia likes = likedMediaRepository.findByUserAndMedia(user, media);
        if (likes == null) {
            likedMediaRepository.save(new LikedMedia(user, media));
            return true;
        }
        likedMediaRepository.deleteById(likes.getId());
        return false;
    }

    public List<LikedMedia> getLikedMedias(User user) {
        return likedMediaRepository.findByUser(user);
    }

    public List<LikedMedia> getUserLikes(Media media) {
        return likedMediaRepository.findByMedia(media);
    }
    // *******************************View Media******************************* //

    public List<ViewMedia> getAllViewedMedia() {
        return viewMediaRepository.findAll();
    }

    public void addView(ViewMedia viewMedia) {
        viewMediaRepository.save(viewMedia);
    }

    public void checkDuplicatedView(User user, Media media) {
        ViewMedia views = viewMediaRepository.findByUserAndMedia(user, media);
        if (views == null)
            viewMediaRepository.save(new ViewMedia(user, media));
    }

    public long getViewsCount(Media media) {
        List<ViewMedia> views = viewMediaRepository.findByMedia(media);
        return views.size();
    }

    // *******************************Album Artist******************************* //

    public List<AlbumArtist> getAllAlbumArtist() {
        return albumArtistRepository.findAll();
    }

    public void addAlbumArtist(AlbumArtist albumArtist) {
        albumArtistRepository.save(albumArtist);
    }

    public List<AlbumArtist> getByAlbum(Album album) {
        return albumArtistRepository.findByAlbum(album);
    }

    public List<AlbumArtist> getByArtist(Artist artist) {
        return albumArtistRepository.findByArtist(artist);
    }

}
