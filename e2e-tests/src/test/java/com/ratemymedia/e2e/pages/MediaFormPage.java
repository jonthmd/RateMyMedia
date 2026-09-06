package com.ratemymedia.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MediaFormPage {

    private final WebDriverWait wait;

    @FindBy(id = "type")
    private WebElement typeSelect;

    @FindBy(id = "titre")
    private WebElement titreInput;

    // --- Champs spécifiques FILM ---
    @FindBy(id = "realisateur")
    private WebElement realisateurInput;

    @FindBy(id = "dureeMinutes")
    private WebElement dureeMinutesInput;

    // --- Champs spécifiques LIVRE ---
    @FindBy(id = "auteurLivre")
    private WebElement auteurLivreInput;

    @FindBy(id = "nombrePages")
    private WebElement nombrePagesInput;

    @FindBy(id = "editeur")
    private WebElement editeurInput;

    // --- Champs spécifiques ALBUM ---
    @FindBy(id = "artiste")
    private WebElement artisteInput;

    @FindBy(id = "nombrePistes")
    private WebElement nombrePistesInput;

    @FindBy(id = "genreMusical")
    private WebElement genreMusicalInput;

    @FindBy(css = ".media-form button[type='submit']")
    private WebElement submitButton;

    public MediaFormPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void selectType(String type) {
        wait.until(ExpectedConditions.visibilityOf(typeSelect));
        new Select(typeSelect).selectByValue(type);
    }

    public void fillTitre(String titre) {
        fill(titreInput, titre);
    }

    public void fillRealisateur(String realisateur) {
        fill(realisateurInput, realisateur);
    }

    public void fillDureeMinutes(String duree) {
        fill(dureeMinutesInput, duree);
    }

    public void fillAuteurLivre(String auteur) {
        fill(auteurLivreInput, auteur);
    }

    public void fillNombrePages(String pages) {
        fill(nombrePagesInput, pages);
    }

    public void fillEditeur(String editeur) {
        fill(editeurInput, editeur);
    }

    public void fillArtiste(String artiste) {
        fill(artisteInput, artiste);
    }

    public void fillNombrePistes(String pistes) {
        fill(nombrePistesInput, pistes);
    }

    public void fillGenreMusical(String genre) {
        fill(genreMusicalInput, genre);
    }

    public void submit() {
        submitButton.click();
        // Sans cette attente, l'étape suivante peut naviguer ailleurs
        // (driver.get()) AVANT que le POST n'ait eu le temps de partir,
        // ce qui annule la requête HTTP encore en vol côté navigateur.
        // On attend donc la redirection vers /medias/{id}, preuve que la
        // création a réellement été traitée par le backend.
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/medias/nouveau")));
    }

    private void fill(WebElement field, String value) {
        field.clear();
        field.sendKeys(value);
    }
}
