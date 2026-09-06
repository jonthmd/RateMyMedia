package com.example.catalogservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("LIVRE")
@Getter
@Setter
@NoArgsConstructor
public class Livre extends Media {

    private String auteur;

    @Positive(message = "Le nombre de pages doit être positif")
    private Integer nombrePages;

    private String editeur;
}
