package com.SocialMediaMongodb.service;

import com.SocialMediaMongodb.model.Artist;
import com.SocialMediaMongodb.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtist() {
        return artistRepository.findAll();

    }

    public Artist getArtist(String id) {
        Optional<Artist> resource = artistRepository.findById(id);
        if (resource.isPresent())
            return resource.get();
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
}
