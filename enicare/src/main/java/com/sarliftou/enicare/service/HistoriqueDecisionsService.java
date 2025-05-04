package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.HistoriqueDecisions;
import com.sarliftou.enicare.entities.StatutDemande;
import com.sarliftou.enicare.repositories.HistoriqueDecisionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueDecisionsService {
    private final HistoriqueDecisionsRepository historiqueDecisionsRepository;

    public List<HistoriqueDecisions> getAllHistorique() {
        return historiqueDecisionsRepository.findAll();
    }

    public List<HistoriqueDecisions> getHistoriqueByAdmin(Long adminId) {
        return historiqueDecisionsRepository.findByAdminId(adminId);
    }

    public List<HistoriqueDecisions> getHistoriqueByStatut(StatutDemande statutDecision) {
        return historiqueDecisionsRepository.findByStatutDecision(statutDecision);
    }

    /*public List<HistoriqueDecisions> getHistoriqueByDateRange(Date dateDebut, Date dateFin) {
        return historiqueDecisionsRepository.findByDateDecisionBetween(dateDebut, dateFin);
    }*/


    public List<HistoriqueDecisions> getHistoriqueByDemandeId(Long demandeId) {
        return historiqueDecisionsRepository.findByDemandeId(demandeId);
    }
}
