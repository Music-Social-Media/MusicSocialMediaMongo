package com.SocialMediaMongodb.repository;

import com.SocialMediaMongodb.model.Album;
import com.SocialMediaMongodb.model.AlbumArtist;
import com.SocialMediaMongodb.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumArtistRepository extends MongoRepository<AlbumArtist, String> {
    List<AlbumArtist> findByAlbum(Album album);

    List<AlbumArtist> findByArtist(Artist artist);
}
