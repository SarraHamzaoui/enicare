package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.Role;
import com.sarliftou.enicare.entities.StatutDemande;
import com.sarliftou.enicare.repositories.DemandeRepository;
import com.sarliftou.enicare.repositories.EtudiantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class DashboardService {
    public final DemandeRepository demandeRepository;

    private EtudiantRepository etudiantRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalDemandes", demandeRepository.count());
        stats.put("demandesEnCours", demandeRepository.countByStatut(StatutDemande.EN_COURS));
        stats.put("demandesAcceptees", demandeRepository.countByStatut(StatutDemande.ACCEPTEE));
        stats.put("demandesRefusees", demandeRepository.countByStatut(StatutDemande.REFUSEE));
        stats.put("totalEtudiants", etudiantRepository.countByRole(Role.ETUDIANT));

        return stats;
    }
}
