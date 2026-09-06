package com.ratemymedia.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MediaDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "note")
    private WebElement noteSelect;

    @FindBy(id = "commentaire")
    private WebElement commentaireInput;

    @FindBy(css = ".review-form button[type='submit']")
    private WebElement submitReviewButton;

    @FindBy(css = ".review-list li .note")
    private List<WebElement> reviewNotes;

    public MediaDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void rate(int note, String commentaire) {
        wait.until(ExpectedConditions.visibilityOf(noteSelect));
        new Select(noteSelect).selectByValue(String.valueOf(note));

        if (commentaire != null && !commentaire.isBlank()) {
            commentaireInput.sendKeys(commentaire);
        }

        submitReviewButton.click();
        waitForSubmissionToFinish();
    }

    public boolean hasReviewWithNote(int note) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".reviews")));
        return reviewNotes.stream().anyMatch(el -> el.getText().startsWith(note + "/5"));
    }

    /**
     * Le bouton passe à "disabled" pendant l'appel HTTP (submitting() côté
     * Angular). On attend d'abord de le VOIR désactivé (s'il ne l'a jamais
     * été, la requête était déjà terminée avant qu'on vérifie), puis on
     * attend qu'il redevienne cliquable avant de poursuivre.
     */
    private void waitForSubmissionToFinish() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(d -> !submitReviewButton.isEnabled());
        } catch (TimeoutException ignored) {
            return;
        }
        wait.until(d -> submitReviewButton.isEnabled());
    }
}
