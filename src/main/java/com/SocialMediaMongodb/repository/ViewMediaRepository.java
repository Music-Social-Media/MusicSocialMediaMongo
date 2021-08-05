package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.LikedMedia;
import com.SocialMediaMongodb.model.Media;
import com.SocialMediaMongodb.model.User;
import com.SocialMediaMongodb.model.ViewMedia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewMediaRepository extends MongoRepository<ViewMedia, String> {
    ViewMedia findByUserAndMedia(User user, Media media);

    List<ViewMedia> findByMedia(Media media);

}
