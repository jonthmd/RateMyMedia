package com.example.catalogservice.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ALBUM")
@Getter
@Setter
@NoArgsConstructor
public class Album extends Media {

    private String artiste;

    @Positive(message = "Le nombre de pistes doit être positif")
    private Integer nombrePistes;

    private String genreMusical;
}
