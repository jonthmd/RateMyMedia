package com.example.catalogservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("FILM")
@Getter
@Setter
@NoArgsConstructor
public class Film extends Media {

    @Positive(message = "La durée doit être positive")
    private Integer dureeMinutes;

    private String realisateur;
}
