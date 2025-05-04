package com.sarliftou.enicare.repositories;

import com.sarliftou.enicare.entities.Justificatif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JustificatifRepository extends JpaRepository<Justificatif, Long> {
    List<Justificatif> findByDemandeEtudiantId(Long etudiantId);
}
