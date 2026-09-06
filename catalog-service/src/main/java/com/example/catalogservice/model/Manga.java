package com.example.catalogservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("MANGA")
@Getter
@Setter
@NoArgsConstructor
public class Manga extends Media {

    private String auteur;

    @Positive(message = "Le nombre de tomes doit être positif")
    private Integer nombreTomes;

    private Boolean enCours;
}
