package com.sarliftou.enicare.repositories;

import com.sarliftou.enicare.entities.HistoriqueDecisions;
import com.sarliftou.enicare.entities.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.time.LocalDateTime;

import java.util.List;

public interface HistoriqueDecisionsRepository extends JpaRepository<HistoriqueDecisions, Long> {
    List<HistoriqueDecisions> findByAdminId(Long adminId);

    List<HistoriqueDecisions> findByStatutDecision(StatutDemande statutDecision);

    //List<HistoriqueDecisions> findByDateDecisionBetween(Date dateDebut, Date dateFin);

    List<HistoriqueDecisions> findByDemandeId(Long demandeId);

}

