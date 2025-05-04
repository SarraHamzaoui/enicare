package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.dto.DemandeAdminRequest;
import com.sarliftou.enicare.dto.DemandeTraitementRequest;
import com.sarliftou.enicare.entities.Demande;
import com.sarliftou.enicare.entities.StatutDemande;
import com.sarliftou.enicare.service.DemandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
public class DemandeController {
    private final DemandeService demandeService;

    @GetMapping
    public ResponseEntity<List<Demande>> getAllDemandes(
            @RequestParam(required = false) StatutDemande statut,
            @RequestParam(required = false) Long etudiantId) {

        List<Demande> demandes;

        if (statut != null && etudiantId != null) {
            demandes = demandeService.findByStatutAndEtudiantId(statut, etudiantId);
        } else if (statut != null) {
            demandes = demandeService.findByStatut(statut);
        } else if (etudiantId != null) {
            demandes = demandeService.findByEtudiantId(etudiantId);
        } else {
            demandes = demandeService.getAllDemandes();
        }

        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        return demandeService.getDemandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/etudiant/{etudiantId}")
    public List<Demande> getDemandesByEtudiant(@PathVariable Long etudiantId) {
        return demandeService.getDemandesByEtudiant(etudiantId);
    }

    @PostMapping("/add")
    public ResponseEntity<Demande> createDemande(@RequestBody Demande demande) {
        Demande savedDemande = demandeService.saveDemande(demande);
        return ResponseEntity.ok(savedDemande);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/mettreEnCoursDemande/{id}")
    public ResponseEntity<Demande> mettreEnCoursDemande(@PathVariable Long id, @RequestBody DemandeAdminRequest request) {
        return ResponseEntity.ok(demandeService.mettreEnCoursDemande(id, request.getAdminId()));
    }

    @PatchMapping("/traiter/{id}")
    public ResponseEntity<Demande> traiterDemande(@PathVariable Long id,@RequestBody DemandeTraitementRequest request) {
        Demande demande = demandeService.traiterDemande(
                id,
                request.getAdminId(),
                request.getDecision(),
                request.getMessage()
        );

        return ResponseEntity.ok(demande);
    }

}
