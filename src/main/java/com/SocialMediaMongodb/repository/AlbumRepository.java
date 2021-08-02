package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<Album, String> {
    Album findByName(String name);
}
