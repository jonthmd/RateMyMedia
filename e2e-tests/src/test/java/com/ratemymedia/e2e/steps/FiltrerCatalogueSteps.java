package com.ratemymedia.e2e.steps;

import com.ratemymedia.e2e.config.TestConfig;
import com.ratemymedia.e2e.context.TestContext;
import com.ratemymedia.e2e.pages.MediaListPage;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;

public class FiltrerCatalogueSteps {

    private final MediaListPage mediaListPage;

    public FiltrerCatalogueSteps(TestContext context) {
        this.mediaListPage = new MediaListPage(context.driver());
    }

    @Quand("je filtre le catalogue sur le type {string}")
    public void jeFiltreLeCatalogue(String type) {
        mediaListPage.open(TestConfig.frontendBaseUrl());
        mediaListPage.filterByType(type);
    }

    @Alors("le média {string} apparaît dans la liste")
    public void leMediaApparaitDansLaListe(String titre) {
        if (!mediaListPage.isMediaVisible(titre)) {
            throw new AssertionError("Le média '" + titre + "' n'apparaît pas dans la liste filtrée");
        }
    }

    @Et("le média {string} n'apparaît pas dans la liste")
    public void leMediaNApparaitPasDansLaListe(String titre) {
        if (mediaListPage.isMediaVisible(titre)) {
            throw new AssertionError("Le média '" + titre + "' apparaît alors qu'il ne devrait pas être présent");
        }
    }
}
