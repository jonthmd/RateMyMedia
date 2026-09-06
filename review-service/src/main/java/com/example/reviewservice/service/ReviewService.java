package com.example.reviewservice.service;

import com.example.reviewservice.client.CatalogClient;
import com.example.reviewservice.exception.MediaNotFoundException;
import com.example.reviewservice.exception.ResourceNotFoundException;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.repository.ReviewRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CatalogClient catalogClient;

    public Review create(Review review) {
        verifyMediaExists(review.getMediaId());
        review.setDateCreation(Instant.now());
        return reviewRepository.save(review);
    }

    public List<Review> findAll(Long mediaId) {
        if (mediaId != null) {
            return reviewRepository.findByMediaId(mediaId);
        }
        return reviewRepository.findAll();
    }

    public Review findById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avis introuvable avec l'id " + id));
    }

    public void delete(String id) {
        Review existing = findById(id);
        reviewRepository.delete(existing);
    }

    /**
     * Vérifie l'existence du média côté catalog-service via Feign.
     * Un FeignException.NotFound (404 renvoyé par catalog-service) est
     * traduit en exception métier propre à review-service.
     */
    private void verifyMediaExists(Long mediaId) {
        try {
            catalogClient.getMediaById(mediaId);
        } catch (FeignException.NotFound e) {
            throw new MediaNotFoundException("Aucun média trouvé dans le catalogue avec l'id " + mediaId);
        }
    }
}
