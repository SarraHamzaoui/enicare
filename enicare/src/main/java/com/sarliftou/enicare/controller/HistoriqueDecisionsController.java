package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.entities.HistoriqueDecisions;
import com.sarliftou.enicare.entities.StatutDemande;
import com.sarliftou.enicare.service.HistoriqueDecisionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/historique")
public class HistoriqueDecisionsController {
    private final HistoriqueDecisionsService historiqueDecisionsService;

    @GetMapping("/all")
    public ResponseEntity<List<HistoriqueDecisions>> getAllHistorique() {
        List<HistoriqueDecisions> historiques = historiqueDecisionsService.getAllHistorique();
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<HistoriqueDecisions>> getHistoriqueByAdmin(@PathVariable Long adminId) {
        List<HistoriqueDecisions> historiques = historiqueDecisionsService.getHistoriqueByAdmin(adminId);
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/statut/{statutDecision}")
    public ResponseEntity<List<HistoriqueDecisions>> getHistoriqueByStatut(@PathVariable StatutDemande statutDecision) {
        List<HistoriqueDecisions> historiques = historiqueDecisionsService.getHistoriqueByStatut(statutDecision);
        return ResponseEntity.ok(historiques);
    }

    /*@GetMapping("/dateRange")
    public ResponseEntity<List<HistoriqueDecisions>> getHistoriqueByDateRange(@RequestParam String dateDebut, @RequestParam String dateFin)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date debut = sdf.parse(dateDebut);  // Convertir la chaîne en Date
        Date fin = sdf.parse(dateFin);

        List<HistoriqueDecisions> historiques = historiqueDecisionsService.getHistoriqueByDateRange(debut, fin);
        return ResponseEntity.ok(historiques);
    }*/

    @GetMapping("/{demandeId}")
    public ResponseEntity<List<HistoriqueDecisions>> getHistorique(@PathVariable Long demandeId) {
        List<HistoriqueDecisions> historique = historiqueDecisionsService.getHistoriqueByDemandeId(demandeId);
        if (historique != null && !historique.isEmpty()) {
            return ResponseEntity.ok(historique);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
