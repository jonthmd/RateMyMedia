package com.example.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe mère de tous les médias du catalogue.
 * Stratégie d'héritage SINGLE_TABLE : une seule table "media" en base,
 * avec une colonne discriminante "type" qui indique la sous-classe concrète
 * (FILM, SERIE, MANGA, LIVRE, ALBUM).
 *
 * Le champ "type" ci-dessous est mappé en lecture seule sur cette même
 * colonne discriminante : il sert à la fois à Spring Data JPA (pour filtrer
 * facilement via findByType) et à Jackson (pour désérialiser le bon
 * sous-type à partir du JSON envoyé par le frontend).
 */
@Entity
@Table(name = "media")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Film.class, name = "FILM"),
        @JsonSubTypes.Type(value = Serie.class, name = "SERIE"),
        @JsonSubTypes.Type(value = Manga.class, name = "MANGA"),
        @JsonSubTypes.Type(value = Livre.class, name = "LIVRE"),
        @JsonSubTypes.Type(value = Album.class, name = "ALBUM")
})
@Getter
@Setter
@NoArgsConstructor
public abstract class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;

    private String imageUrl;

    private Integer annee;

    @Column(length = 2000)
    private String description;

    /**
     * Champ en lecture seule, aligné sur la colonne discriminante JPA.
     * Alimenté automatiquement par Hibernate, jamais écrit manuellement.
     */
    @Column(name = "type", insertable = false, updatable = false)
    private String type;
}
