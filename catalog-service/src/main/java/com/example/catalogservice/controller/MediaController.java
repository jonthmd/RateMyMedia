package com.example.catalogservice.controller;

import com.example.catalogservice.model.Media;
import com.example.catalogservice.service.MediaService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoints REST du catalogue.
 * Exemple d'appel pour ajouter un film :
 * POST /api/medias
 * {
 * "type": "FILM",
 * "titre": "Inception",
 * "annee": 2010,
 * "dureeMinutes": 148,
 * "realisateur": "Christopher Nolan"
 * }
 */
@RestController
@RequestMapping("/api/medias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // à restreindre à l'URL du frontend Angular en prod
//@Tag(name = "Media")
public class MediaController {

    private final MediaService mediaService;

    @GetMapping
//    @Operation(summary = "All media.")
    public List<Media> getAll(@RequestParam(required = false) String type) {
        return mediaService.findAll(type);
    }

    @GetMapping("/{id}")
//    @Operation(summary = "Get a media.")
    public Media getById(@PathVariable Long id) {
        return mediaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Create new media.")
    public Media create(@Valid @RequestBody Media media) {
        return mediaService.create(media);
    }

    @PutMapping("/{id}")
//    @Operation(summary = "Update a media.")
    public Media update(@PathVariable Long id, @Valid @RequestBody Media media) {
        return mediaService.update(id, media);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @Operation(summary = "Delete a media.")
    public void delete(@PathVariable Long id) {
        mediaService.delete(id);
    }
}
