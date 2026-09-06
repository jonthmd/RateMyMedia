package com.example.reviewservice.exception;

/**
 * Levée lorsqu'un avis référence un mediaId qui n'existe pas dans
 * catalog-service (vérifié via l'appel Feign). Séparée de
 * ResourceNotFoundException car l'origine du problème est différente :
 * ici la ressource manquante est dans un AUTRE service.
 */
public class MediaNotFoundException extends RuntimeException {

    public MediaNotFoundException(String message) {
        super(message);
    }
}
