package com.ratemymedia.e2e.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratemymedia.e2e.config.TestConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Prépare des médias directement via l'API REST de catalog-service, sans
 * passer par l'UI. Utile pour les scénarios qui ont seulement besoin qu'un
 * média EXISTE déjà (le noter, le filtrer) : l'ajout via le formulaire est
 * lui-même testé dans son propre scénario dédié (ajouter_media.feature),
 * pas besoin de le repasser à chaque fois — ça rend les autres scénarios
 * plus rapides et indépendants les uns des autres.
 */
public final class CatalogApiClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private CatalogApiClient() {
    }

    public static long createFilm(String titre) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("type", "FILM");
        payload.put("titre", titre);
        payload.put("annee", 2010);
        payload.put("dureeMinutes", 120);
        payload.put("realisateur", "Auteur de test");
        return create(payload);
    }

    public static long createLivre(String titre) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("type", "LIVRE");
        payload.put("titre", titre);
        payload.put("annee", 2000);
        payload.put("auteur", "Auteur de test");
        payload.put("nombrePages", 300);
        return create(payload);
    }

    private static long create(Map<String, Object> payload) {
        try {
            String json = MAPPER.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TestConfig.catalogApiBaseUrl() + "/api/medias"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 201) {
                throw new IllegalStateException(
                        "catalog-service a répondu " + response.statusCode()
                                + " au lieu de 201. Corps de la réponse : " + response.body());
            }

            JsonNode body = MAPPER.readTree(response.body());
            JsonNode idNode = body.get("id");
            if (idNode == null) {
                throw new IllegalStateException("La réponse ne contient pas de champ 'id' : " + response.body());
            }
            return idNode.asLong();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Échec de la création du média via l'API : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Échec de la création du média via l'API (" + e.getClass().getSimpleName()
                            + ") : " + e.getMessage(), e);
        }
    }
}
