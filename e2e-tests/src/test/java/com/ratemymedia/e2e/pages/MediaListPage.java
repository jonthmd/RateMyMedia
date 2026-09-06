package com.ratemymedia.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MediaListPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "type-filter")
    private WebElement filterSelect;

    // Sans @CacheLookup : Selenium relance findElements() à chaque accès à
    // cette liste, ce qui évite les StaleElementReferenceException quand
    // Angular reconstruit le DOM après un appel HTTP.
    @FindBy(css = ".card")
    private List<WebElement> cards;

    @FindBy(linkText = "Ajouter un média")
    private WebElement addLink;

    private static final By LOADING_INDICATOR = By.cssSelector(".status");

    public MediaListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/medias");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        LoadingWaits.waitUntilGone(driver, LOADING_INDICATOR, Duration.ofSeconds(10));
    }

    public void filterByType(String type) {
        wait.until(ExpectedConditions.visibilityOf(filterSelect));
        new Select(filterSelect).selectByValue(type);
        LoadingWaits.waitUntilGone(driver, LOADING_INDICATOR, Duration.ofSeconds(10));
    }

    public boolean isMediaVisible(String titre) {
        return cards.stream().anyMatch(card -> card.getText().contains(titre));
    }

    public void openMediaByTitle(String titre) {
        WebElement card = cards.stream()
                .filter(c -> c.getText().contains(titre))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Média introuvable dans la liste : " + titre));
        card.click();
    }

    public void goToAddMediaForm() {
        addLink.click();
    }
}
