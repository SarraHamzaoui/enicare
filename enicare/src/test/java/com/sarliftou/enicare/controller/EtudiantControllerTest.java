package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.entities.Etudiant;
import com.sarliftou.enicare.service.EtudiantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtudiantController.class)
@Import(EtudiantControllerTest.MockConfig.class)
class EtudiantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EtudiantService etudiantService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public EtudiantService etudiantService() {
            return Mockito.mock(EtudiantService.class);
        }
    }

    @Test
    void getAllEtudiant_ShouldReturnListOfEtudiants() throws Exception {
        System.out.println("▶ Lancement du test getAllEtudiant_ShouldReturnListOfEtudiants...");

        Etudiant etudiant1 = new Etudiant();
        etudiant1.setId(1L);
        etudiant1.setNom("John Doe");
        etudiant1.setEmail("john.doe@example.com");

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setId(2L);
        etudiant2.setNom("Jane Doe");
        etudiant2.setEmail("jane.doe@example.com");

        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantService.getAllEtudiant()).thenReturn(etudiants);

        mockMvc.perform(get("/api/etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nom").value("John Doe"))
                .andExpect(jsonPath("$[1].nom").value("Jane Doe"));

        System.out.println("Test terminé avec succès : 2 étudiants récupérés !");
    }



    @Test
    void getEtudiantById_shouldReturnEtudiant() throws Exception {
        Long id = 1L;
        Etudiant etudiant = new Etudiant();
        etudiant.setId(id);
        etudiant.setNom("Hamzaoui");
        etudiant.setPrenom("Sarra");
        etudiant.setEmail("sarra@gmail.com");

        // Simuler le service qui retourne un étudiant
        when(etudiantService.getEtudiantById(id)).thenReturn(Optional.of(etudiant));

        System.out.println("▶ Test getEtudiantById lancé...");

        mockMvc.perform(get("/api/etudiants/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nom").value("Hamzaoui"))
                .andExpect(jsonPath("$.prenom").value("Sarra"))
                .andExpect(jsonPath("$.email").value("sarra@gmail.com"));

        System.out.println("Test getEtudiantById terminé avec succès !");
    }


}
