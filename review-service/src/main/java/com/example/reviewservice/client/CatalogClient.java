package com.example.reviewservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client Feign déclaratif : Spring génère l'implémentation à partir de cette
 * interface. Un simple appel à catalogClient.getMediaById(id) déclenche en
 * coulisse un GET HTTP vers catalog-service.
 *
 * L'URL est configurée dans application.yml (catalog-service.url), ce qui
 * évite de la coder en dur ici et permet de la surcharger facilement en
 * environnement Docker (nom du service) ou en CI.
 */
@FeignClient(name = "catalog-service", url = "${catalog-service.url}")
public interface CatalogClient {

    @GetMapping("/api/medias/{id}")
    MediaSummary getMediaById(@PathVariable("id") Long id);
}
