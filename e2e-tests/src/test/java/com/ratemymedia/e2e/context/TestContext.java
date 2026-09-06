package com.ratemymedia.e2e.context;

import org.openqa.selenium.WebDriver;

/**
 * Instance unique par scénario, partagée entre les Hooks et toutes les
 * classes de steps, grâce à l'injection de dépendances de
 * cucumber-picocontainer (chaque classe de steps qui déclare un
 * constructeur prenant un TestContext reçoit la MÊME instance pour la
 * durée du scénario).
 */
public class TestContext {

    private WebDriver driver;

    public WebDriver driver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
