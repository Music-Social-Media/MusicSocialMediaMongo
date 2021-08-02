package com.SocialMediaMongodb.service;

import com.SocialMediaMongodb.model.Media;
import com.SocialMediaMongodb.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMedia(String id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isPresent())
            return media.get();
        else
            return null;
    }

    public List<Media> getMediaByAlbumID(String id) {
        var medias = (List<Media>) mediaRepository.findByAlbumID(id);
        return medias;
    }

    public void addOrUpdateMedia(Media media) {
        if (media != null)
            mediaRepository.save(media);
    }

    public boolean deleteMedia(String id) {
        Optional<Media> resource = mediaRepository.findById(id);
        if (resource.isPresent()) {
            mediaRepository.deleteById(id);
            return true;
        } else
            return false;

    }
//
//    public void updateMedia(Media media) {
//        if (media.getMediaID() == null)
//            mediaRepository.save(media);
//        else {
//            Optional<Media> existingMedia = mediaRepository.findById(media.getMediaID());
//            if (existingMedia.isPresent()) {
//                Media newMedia = existingMedia.get();
//                newMedia.setName(media.getName());
//                newMedia.setGenre(media.getGenre());
//                newMedia.setPublishDate(media.getPublishDate());
//                newMedia.setLength(media.getLength());
//                newMedia.setScore(media.getScore());
//                mediaRepository.save(newMedia);
//            }
//        }
//    }

}
