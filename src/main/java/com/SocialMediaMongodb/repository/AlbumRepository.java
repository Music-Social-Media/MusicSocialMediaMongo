package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
    public Album findByName(String name);
}
