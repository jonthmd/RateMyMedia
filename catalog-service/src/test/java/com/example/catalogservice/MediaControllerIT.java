package com.example.catalogservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MediaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void devrait_creer_un_film_et_le_retrouver_avec_ses_champs_specifiques() throws Exception {
        Map<String, Object> film = new HashMap<>();
        film.put("type", "FILM");
        film.put("titre", "Inception");
        film.put("annee", 2010);
        film.put("dureeMinutes", 148);
        film.put("realisateur", "Christopher Nolan");

        mockMvc.perform(post("/api/medias")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("FILM"))
                .andExpect(jsonPath("$.realisateur").value("Christopher Nolan"));
    }

    @Test
    void devrait_filtrer_les_medias_par_type() throws Exception {
        Map<String, Object> livre = new HashMap<>();
        livre.put("type", "LIVRE");
        livre.put("titre", "Dune");
        livre.put("auteur", "Frank Herbert");
        livre.put("nombrePages", 688);

        mockMvc.perform(post("/api/medias")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(livre)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/medias").param("type", "LIVRE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("LIVRE"))
                .andExpect(jsonPath("$[0].auteur").value("Frank Herbert"));
    }
}
