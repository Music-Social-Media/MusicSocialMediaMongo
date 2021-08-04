//package com.SocialMediaMongodb.repository;
//
//import com.SocialMediaMongodb.model.Album;
//import com.SocialMediaMongodb.model.Artist;
//import com.SocialMediaMongodb.model.ArtistAlbum;
//import jdk.jfr.Registered;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import java.util.List;
//
//@Registered
//public interface ArtistAlbumRepository extends MongoRepository<ArtistAlbum, String> {
//    public List<Album> findByArtist(String artistID);
//
//    public List<Artist> findByAlbum(String albumID);
//}
