package com.ratemymedia.e2e.steps;

import com.ratemymedia.e2e.config.TestConfig;
import com.ratemymedia.e2e.context.TestContext;
import com.ratemymedia.e2e.pages.MediaFormPage;
import com.ratemymedia.e2e.pages.MediaListPage;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonnéque;
import io.cucumber.java.fr.Quand;

public class AjouterMediaSteps {

    private final MediaListPage mediaListPage;
    private final MediaFormPage mediaFormPage;

    public AjouterMediaSteps(TestContext context) {
        this.mediaListPage = new MediaListPage(context.driver());
        this.mediaFormPage = new MediaFormPage(context.driver());
    }

    @Etantdonnéque("je suis sur le formulaire d'ajout d'un média")
    public void jeSuisSurLeFormulaireDAjout() {
        mediaListPage.open(TestConfig.frontendBaseUrl());
        mediaListPage.goToAddMediaForm();
    }

    @Quand("je choisis le type {string} et je renseigne le titre {string}")
    public void jeChoisisLeTypeEtLeTitre(String type, String titre) {
        mediaFormPage.selectType(type);
        mediaFormPage.fillTitre(titre);
    }

    @Et("je renseigne le réalisateur {string} et une durée de {string} minutes")
    public void jeRenseigneLeRealisateur(String realisateur, String duree) {
        mediaFormPage.fillRealisateur(realisateur);
        mediaFormPage.fillDureeMinutes(duree);
    }

    @Et("je renseigne l'auteur {string}, {string} pages et l'éditeur {string}")
    public void jeRenseigneLAuteurLivre(String auteur, String pages, String editeur) {
        mediaFormPage.fillAuteurLivre(auteur);
        mediaFormPage.fillNombrePages(pages);
        mediaFormPage.fillEditeur(editeur);
    }

    @Et("je renseigne l'artiste {string}, {string} pistes et le genre {string}")
    public void jeRenseigneLArtiste(String artiste, String pistes, String genre) {
        mediaFormPage.fillArtiste(artiste);
        mediaFormPage.fillNombrePistes(pistes);
        mediaFormPage.fillGenreMusical(genre);
    }

    @Et("je valide le formulaire")
    public void jeValideLeFormulaire() {
        mediaFormPage.submit();
    }

    @Alors("le média {string} apparaît dans le catalogue")
    public void leMediaApparaitDansLeCatalogue(String titre) {
        mediaListPage.open(TestConfig.frontendBaseUrl());
        if (!mediaListPage.isMediaVisible(titre)) {
            throw new AssertionError("Le média '" + titre + "' n'apparaît pas dans le catalogue");
        }
    }
}
