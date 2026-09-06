package com.example.catalogservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("SERIE")
@Getter
@Setter
@NoArgsConstructor
public class Serie extends Media {

    @Positive(message = "Le nombre de saisons doit être positif")
    private Integer nombreSaisons;

    @Positive(message = "Le nombre d'épisodes doit être positif")
    private Integer nombreEpisodes;

    private String plateforme;

    private Boolean enCours;
}
