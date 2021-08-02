package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
    @Query("{'media.id' : ?0}")
    List<Media> findByAlbumID(String id);
}
