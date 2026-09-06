package com.ratemymedia.e2e.config;

/**
 * Centralise les URLs et le choix de plateforme, pilotés par les propriétés
 * système passées à Maven (voir les profils "web"/"android" du pom.xml).
 *
 * Point clé : l'émulateur Android ne voit pas "localhost" comme la machine
 * hôte, il faut l'alias spécial 10.0.2.2. C'est pour ça que les URLs par
 * défaut changent selon la plateforme.
 */
public final class TestConfig {

    private TestConfig() {
    }

    public static String platform() {
        return System.getProperty("platform", "web");
    }

    public static boolean isAndroid() {
        return "android".equals(platform());
    }

    public static String frontendBaseUrl() {
        String defaultUrl = isAndroid() ? "http://10.0.2.2:4200" : "http://localhost:4200";
        return System.getProperty("frontendUrl", defaultUrl);
    }

    public static String catalogApiBaseUrl() {
        // Contrairement à frontendBaseUrl(), cet appel HTTP est fait
        // directement par la JVM qui exécute les tests (CatalogApiClient),
        // laquelle tourne toujours sur la machine hôte — jamais depuis
        // l'intérieur de l'émulateur, même en configuration "android".
        // "localhost" est donc toujours correct ici ; 10.0.2.2 n'aurait de
        // sens que pour du code exécuté DANS l'émulateur (le navigateur).
        return System.getProperty("catalogApiUrl", "http://localhost:8081");
    }
}
