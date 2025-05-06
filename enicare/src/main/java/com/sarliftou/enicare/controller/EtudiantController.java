package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.entities.Etudiant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sarliftou.enicare.service.EtudiantService;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {
    private final EtudiantService etudiantService;

    @GetMapping
    public List<Etudiant> getAllEtudiant(){
        return etudiantService.getAllEtudiant();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id){
        return etudiantService.getEtudiantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<String> createEtudiant(@RequestBody Etudiant etudiant) {
        try {
            etudiantService.saveEtudiant(etudiant);

            return ResponseEntity.status(HttpStatus.CREATED).body("Etudiant créé avec succès et un code de vérification" +
                    " a été envoyé à \"" + etudiant.getEmail());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public String verifyUser(@RequestParam String email, @RequestParam String code) {
        boolean verified = etudiantService.verifyCode(email, code);
        return verified ? "Compte vérifié avec succès !" : "Code incorrect.";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id){
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }

}
