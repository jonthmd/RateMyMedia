package com.ratemymedia.e2e.steps;

import com.ratemymedia.e2e.helpers.CatalogApiClient;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonnéque;

/**
 * Steps de préparation de données, réutilisables par plusieurs
 * fonctionnalités (noter_media, filtrer_catalogue...). On passe par l'API
 * REST plutôt que par l'UI : ces scénarios ne testent pas l'ajout en
 * lui-même (déjà couvert par ajouter_media.feature), seulement le
 * comportement qui suit une fois qu'un média existe déjà.
 */
public class DonneesSteps {

    @Etantdonnéque("le média {string} de type {string} existe dans le catalogue")
    public void leMediaExisteDansLeCatalogue(String titre, String type) {
        creerMedia(titre, type);
    }

    @Et("que le média {string} de type {string} existe dans le catalogue")
    public void etQueLeMediaExisteDansLeCatalogue(String titre, String type) {
        creerMedia(titre, type);
    }

    private void creerMedia(String titre, String type) {
        switch (type) {
            case "FILM" -> CatalogApiClient.createFilm(titre);
            case "LIVRE" -> CatalogApiClient.createLivre(titre);
            default -> throw new IllegalArgumentException("Type non géré pour la création rapide : " + type);
        }
    }
}
