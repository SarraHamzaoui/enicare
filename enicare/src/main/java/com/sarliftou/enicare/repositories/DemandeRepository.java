package com.sarliftou.enicare.repositories;

import com.sarliftou.enicare.entities.Demande;
import com.sarliftou.enicare.entities.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Long> {
    List<Demande> findByEtudiantId(Long etudiantId);
    List<Demande> findByStatut(StatutDemande statut);
    List<Demande> findByStatutAndEtudiantId(StatutDemande  statut, Long etudiantId);
    long countByStatut(StatutDemande  statut);
}

