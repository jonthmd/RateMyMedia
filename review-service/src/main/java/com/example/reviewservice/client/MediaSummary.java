package com.example.reviewservice.client;

/**
 * Représentation minimale d'un média, telle que renvoyée par catalog-service.
 * On ne reprend que les champs communs à tous les types de médias :
 * review-service n'a pas besoin des champs spécifiques (dureeMinutes,
 * nombreTomes...) pour vérifier qu'un média existe.
 */
public record MediaSummary(
        Long id,
        String type,
        String titre,
        String imageUrl,
        Integer annee,
        String description
) {
}
