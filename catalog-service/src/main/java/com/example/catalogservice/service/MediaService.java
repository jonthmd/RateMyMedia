package com.example.catalogservice.service;

import com.example.catalogservice.exception.ResourceNotFoundException;
import com.example.catalogservice.model.Media;
import com.example.catalogservice.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    public List<Media> findAll(String type) {
        if (type != null && !type.isBlank()) {
            return mediaRepository.findByType(type.toUpperCase());
        }
        return mediaRepository.findAll();
    }

    public Media findById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Média introuvable avec l'id " + id));
    }

    public Media create(Media media) {
        return mediaRepository.save(media);
    }

    public Media update(Long id, Media media) {
        Media existing = findById(id);
        media.setId(existing.getId());
        return mediaRepository.save(media);
    }

    public void delete(Long id) {
        Media existing = findById(id);
        mediaRepository.delete(existing);
    }
}
