package com.example.reviewservice.controller;

import com.example.reviewservice.model.Review;
import com.example.reviewservice.service.ReviewService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Exemple d'appel pour noter un film déjà présent dans le catalogue :
 * POST /api/reviews
 * {
 *   "mediaId": 1,
 *   "note": 5,
 *   "commentaire": "Un classique du genre !"
 * }
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
//@Tag(name = "Review.")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
//    @Operation(summary = "All review.")
    public List<Review> getAll(@RequestParam(required = false) Long mediaId) {
        return reviewService.findAll(mediaId);
    }

    @GetMapping("/{id}")
//    @Operation(summary = "Get a review.")
    public Review getById(@PathVariable String id) {
        return reviewService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Create new review.")
    public Review create(@Valid @RequestBody Review review) {
        return reviewService.create(review);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @Operation(summary = "Delete a review.")
    public void delete(@PathVariable String id) {
        reviewService.delete(id);
    }
}
