package com.ratemymedia.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Sans cette attente, Selenium/Appium peuvent interroger le DOM AVANT que
 * la réponse HTTP ne soit revenue et qu'Angular n'ait re-rendu la page,
 * provoquant des faux échecs intermittents ("tests flaky").
 */
final class LoadingWaits {

    private LoadingWaits() {
    }

    static void waitUntilGone(WebDriver driver, By indicator, Duration timeout) {
        // Étape 1 : on laisse une courte fenêtre pour VOIR apparaître
        // l'indicateur. S'il n'apparaît jamais (requête déjà terminée
        // avant que Selenium ne vérifie), ce n'est pas une erreur.
        try {
            new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(ExpectedConditions.visibilityOfElementLocated(indicator));
        } catch (TimeoutException ignored) {
            return;
        }

        // Étape 2 : maintenant qu'on sait qu'il est apparu, on attend
        // vraiment sa disparition avant de continuer.
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(indicator));
    }
}
