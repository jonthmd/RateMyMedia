package com.ratemymedia.e2e.drivers;

import com.ratemymedia.e2e.config.TestConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Fabrique de WebDriver selon la plateforme ciblée (propriété système
 * "platform", positionnée par les profils Maven "web"/"android") :
 *
 * - "web" (par défaut) : Selenium + Chrome desktop, avec l'émulation
 *   mobile de Chrome DevTools (résolution + user-agent d'un Pixel 7).
 *   Aucune infrastructure externe requise.
 *
 * - "android" : Appium + UiAutomator2, piloté sur un émulateur Android
 *   déjà démarré, ouvrant directement Chrome dessus. Nécessite un
 *   serveur Appium lancé en local (`appium`, écoutant sur le port 4723)
 *   et un AVD démarré.
 *
 * Les Page Objects ne manipulent que l'interface WebDriver commune :
 * AndroidDriver EN HÉRITE, donc le reste du code de test (Page Objects,
 * steps Cucumber) est strictement identique quelle que soit la plateforme.
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        return TestConfig.isAndroid() ? createAndroidDriver() : createWebDriver();
    }

    private static WebDriver createWebDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Pixel 7");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createAndroidDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(System.getProperty("deviceName", "emulator-5554"))
                .withBrowserName("Chrome")
                .setNewCommandTimeout(java.time.Duration.ofMinutes(2));

        // Appium a besoin d'une version de chromedriver qui corresponde
        // exactement à la version de Chrome installée sur l'émulateur.
        // Cette capability lui demande de la télécharger automatiquement
        // au lieu d'exiger une installation manuelle préalable (nécessite
        // que la machine qui fait tourner le serveur Appium ait accès à
        // internet).
        options.setCapability("appium:chromedriverAutodownload", true);

        String appiumServerUrl = System.getProperty("appiumServerUrl", "http://127.0.0.1:4723");

        try {
            return new AndroidDriver(new URL(appiumServerUrl), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("URL du serveur Appium invalide : " + appiumServerUrl, e);
        }
    }
}
