package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.entities.Justificatif;
import com.sarliftou.enicare.service.JustificatifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/justificatifs")
public class JustificatifController {
    private final JustificatifService justificatifService;

    @PostMapping("/add")
    public ResponseEntity<Justificatif> createJustificatif(@RequestBody Justificatif justificatif) {
        try {
            Justificatif createdJustificatif = justificatifService.createJustificatif(justificatif);
            return new ResponseEntity<>(createdJustificatif, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Justificatif>> getAllJustificatifs() {
        List<Justificatif> justificatifs = justificatifService.getAllJustificatifs();
        return new ResponseEntity<>(justificatifs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Justificatif> getJustificatifById(@PathVariable Long id) {
        Optional<Justificatif> justificatif = justificatifService.getJustificatifById(id);
        return justificatif.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJustificatif(@PathVariable Long id) {
        justificatifService.deleteJustificatif(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Justificatif> updateJustificatif(@PathVariable Long id,
                                                           @RequestBody Justificatif justificatif) {
        Justificatif existingJustificatif = justificatifService.getJustificatifById(id)
                .orElse(null);

        if (existingJustificatif == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (justificatif.getUrl() != null) {
            existingJustificatif.setUrl(justificatif.getUrl());
        }
        if (justificatif.getCommentaire() != null) {
            existingJustificatif.setCommentaire(justificatif.getCommentaire());
        }
        if (justificatif.getDemande() != null) {
            existingJustificatif.setDemande(justificatif.getDemande());
        }

        Justificatif updatedJustificatif = justificatifService.updateJustificatif(existingJustificatif);

        return new ResponseEntity<>(updatedJustificatif, HttpStatus.OK);
    }


    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Justificatif>> getJustificatifByEtudiantId(@PathVariable Long etudiantId) {
        List<Justificatif> justificatifs = justificatifService.findByDemandeEtudiantId(etudiantId);
        if (justificatifs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(justificatifs, HttpStatus.OK);
    }

}
