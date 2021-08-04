package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
    List<Media> findByAlbum(String id);
}
