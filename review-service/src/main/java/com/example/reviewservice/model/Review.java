package com.example.reviewservice.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Document MongoDB représentant l'avis d'un utilisateur sur un média.
 * "mediaId" fait référence à un Media du catalog-service, sans jointure
 * possible (bases différentes) : la cohérence est vérifiée via un appel
 * Feign au moment de la création.
 */
@Document(collection = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    private String id;

    @NotNull(message = "L'identifiant du média est obligatoire")
    private Long mediaId;

    @NotNull(message = "La note est obligatoire")
    @Min(value = 0, message = "La note minimale est 0")
    @Max(value = 5, message = "La note maximale est 5")
    private Integer note;

    @Size(max = 1000, message = "Le commentaire ne peut pas dépasser 1000 caractères")
    private String commentaire;

    private Instant dateCreation;
}
