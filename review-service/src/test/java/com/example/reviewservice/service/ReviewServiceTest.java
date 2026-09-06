package com.example.reviewservice.service;

import com.example.reviewservice.client.CatalogClient;
import com.example.reviewservice.client.MediaSummary;
import com.example.reviewservice.exception.MediaNotFoundException;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.repository.ReviewRepository;
import feign.FeignException;
import feign.Request;
import feign.Request.HttpMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Test unitaire : on mocke le ReviewRepository (pas de vraie base Mongo)
 * et le CatalogClient (pas de vrai appel HTTP vers catalog-service).
 * Ça permet de tester la logique métier de ReviewService de façon isolée
 * et rapide, sans dépendance externe.
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CatalogClient catalogClient;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void devrait_creer_un_avis_si_le_media_existe() {
        Review review = new Review();
        review.setMediaId(1L);
        review.setNote(5);

        when(catalogClient.getMediaById(1L))
                .thenReturn(new MediaSummary(1L, "FILM", "Inception", null, 2010, null));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Review result = reviewService.create(review);

        assertThat(result.getDateCreation()).isNotNull();
        assertThat(result.getMediaId()).isEqualTo(1L);
    }

    @Test
    void devrait_rejeter_un_avis_si_le_media_n_existe_pas() {
        Review review = new Review();
        review.setMediaId(999L);
        review.setNote(3);

        Request request = Request.create(HttpMethod.GET, "/api/medias/999",
                Collections.emptyMap(), null, StandardCharsets.UTF_8, null);
        when(catalogClient.getMediaById(999L))
                .thenThrow(new FeignException.NotFound("not found", request, null, null));

        assertThatThrownBy(() -> reviewService.create(review))
                .isInstanceOf(MediaNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void devrait_retourner_l_avis_par_id() {
        Review review = new Review();
        review.setId("abc123");
        when(reviewRepository.findById("abc123")).thenReturn(Optional.of(review));

        Review result = reviewService.findById("abc123");

        assertThat(result.getId()).isEqualTo("abc123");
    }
}
