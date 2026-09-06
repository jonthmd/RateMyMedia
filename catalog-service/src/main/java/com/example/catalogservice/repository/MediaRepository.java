package com.example.catalogservice.repository;

import com.example.catalogservice.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    /**
     * Filtre les médias par type (FILM, SERIE, MANGA, LIVRE, ALBUM),
     * en s'appuyant directement sur la colonne discriminante.
     */
    List<Media> findByType(String type);
}
