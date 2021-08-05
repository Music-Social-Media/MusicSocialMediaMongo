package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikedMediaRepository extends MongoRepository<LikedMedia, String> {
    LikedMedia findByUserAndMedia(User user, Media media);

    List<LikedMedia> findByUser(User user);

    List<LikedMedia> findByMedia(Media media);

}
