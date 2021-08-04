package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Artist;
import com.SocialMediaMongodb.model.FollowArtist;
import com.SocialMediaMongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FollowRepository extends MongoRepository<FollowArtist, String> {
    FollowArtist findByUserAndArtist(User user, Artist artist);

}
