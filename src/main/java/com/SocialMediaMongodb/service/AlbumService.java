package com.SocialMediaMongodb.service;

import com.SocialMediaMongodb.model.Album;
import com.SocialMediaMongodb.model.Media;
import com.SocialMediaMongodb.repository.AlbumRepository;
import com.SocialMediaMongodb.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

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
        Optional<Album> album = Optional.ofNullable(albumRepository.findByName(name));
        if (album.isPresent())
            return album.get();
        else
            return null;
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

}
