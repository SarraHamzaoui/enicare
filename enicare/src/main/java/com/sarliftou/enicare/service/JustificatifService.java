package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.Demande;
import com.sarliftou.enicare.entities.Justificatif;
import com.sarliftou.enicare.entities.StatutDemande;
import com.sarliftou.enicare.repositories.DemandeRepository;
import com.sarliftou.enicare.repositories.JustificatifRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JustificatifService {
    private final JustificatifRepository justificatifRepository;
    private final DemandeRepository demandeRepository;

    public Justificatif createJustificatif(Justificatif justificatif) {
        Optional<Demande> demandeOptional = demandeRepository.findById(justificatif.getDemande().getId());
        if (!demandeOptional.isPresent()) {
            throw new RuntimeException("Demande non trouvée.");
        }

        Demande demande = demandeOptional.get();

        if (!demande.getStatut().equals(StatutDemande.EN_ATTENTE_JUSTIFICATIFS)) {
            throw new RuntimeException("Impossible d'ajouter un justificatif. La demande n'est pas en attente de justificatifs.");
        }

        justificatif.setDemande(demande);
        return justificatifRepository.save(justificatif);
    }

    public Optional<Justificatif> getJustificatifById(Long id) {
        return justificatifRepository.findById(id);
    }

    public List<Justificatif> getAllJustificatifs() {
        return justificatifRepository.findAll();
    }

    public void deleteJustificatif(Long id) {
        justificatifRepository.deleteById(id);
    }

    public Justificatif updateJustificatif(Justificatif justificatif) {
        return justificatifRepository.save(justificatif); // Sauvegarde le justificatif mis à jour
    }

    public List<Justificatif> findByDemandeEtudiantId(Long etudiantId) {
        return justificatifRepository.findByDemandeEtudiantId(etudiantId);
    }



}
