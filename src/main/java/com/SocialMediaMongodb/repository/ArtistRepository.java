package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
    List<Artist> findByFirstnameAndLastnameAndBirthdate(String firstname, String lastname, String birthdate);

//    Artist findByName(String firstName, String lastName);
}
