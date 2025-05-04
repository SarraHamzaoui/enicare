package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.entities.Administrateur;
import com.sarliftou.enicare.service.AdministrateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
@RequiredArgsConstructor
public class AdministrateurController {
    private final AdministrateurService administrateurService;

    @GetMapping
    public List<Administrateur> getAllAdministrateur(){
        return administrateurService.getAllAdministrateur();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrateur> getAdministrateurById(@PathVariable Long id){
        return administrateurService.getAdministrateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<String> createEtudiant(@RequestBody Administrateur administrateur) {
        try {
            administrateurService.saveAdministrateur(administrateur);

            return ResponseEntity.status(HttpStatus.CREATED).body("Administrateur créé avec succès et un code de vérification" +
                    " a été envoyé à \"" + administrateur.getEmail());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public String verifyUser(@RequestParam String email, @RequestParam String code) {
        boolean verified = administrateurService.verifyCode(email, code);
        return verified ? "Compte vérifié avec succès !" : "Code incorrect.";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrateur(@PathVariable Long id){
        administrateurService.deleteAdministrateur(id);
        return ResponseEntity.noContent().build();
    }
}
