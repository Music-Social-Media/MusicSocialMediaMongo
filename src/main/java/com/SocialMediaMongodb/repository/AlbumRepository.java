package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
    List<Album> findByArtist(String id);

    Album findByName(String name);
}
