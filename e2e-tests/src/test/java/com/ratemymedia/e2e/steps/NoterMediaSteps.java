package com.ratemymedia.e2e.steps;

import com.ratemymedia.e2e.config.TestConfig;
import com.ratemymedia.e2e.context.TestContext;
import com.ratemymedia.e2e.pages.MediaDetailPage;
import com.ratemymedia.e2e.pages.MediaListPage;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Quand;

public class NoterMediaSteps {

    private final MediaListPage mediaListPage;
    private final MediaDetailPage mediaDetailPage;

    public NoterMediaSteps(TestContext context) {
        this.mediaListPage = new MediaListPage(context.driver());
        this.mediaDetailPage = new MediaDetailPage(context.driver());
    }

    @Quand("je consulte la fiche du média {string}")
    public void jeConsulteLaFicheDuMedia(String titre) {
        mediaListPage.open(TestConfig.frontendBaseUrl());
        mediaListPage.openMediaByTitle(titre);
    }

    @Et("je lui attribue la note de {int} avec le commentaire {string}")
    public void jeLuiAttribueLaNote(int note, String commentaire) {
        mediaDetailPage.rate(note, commentaire);
    }

    @Alors("l'avis avec la note {int} apparaît dans la liste des avis")
    public void lAvisApparaitDansLaListe(int note) {
        if (!mediaDetailPage.hasReviewWithNote(note)) {
            throw new AssertionError("Aucun avis avec la note " + note + " trouvé dans la liste");
        }
    }
}
