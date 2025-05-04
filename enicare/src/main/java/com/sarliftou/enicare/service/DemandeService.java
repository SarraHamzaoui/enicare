package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.*;
import com.sarliftou.enicare.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandeService {
    private final DemandeRepository demandeRepository;
    private final EtudiantRepository etudiantRepository;
    private final NotificationRepository notificationRepository;
    private final AdministrateurRepository administrateurRepository;
    private final HistoriqueDecisionsRepository historiqueDecisionsRepository;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    public List<Demande> getDemandesByEtudiant(Long etudiantId) {
        return demandeRepository.findByEtudiantId(etudiantId);
    }

    public Demande saveDemande(Demande demande) {
        return etudiantRepository.findById(demande.getEtudiant().getId())
                .map(etudiant -> {
                    demande.setEtudiant(etudiant);
                    return demandeRepository.save(demande);
                })
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
    }

    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    public Demande mettreEnCoursDemande(Long id,Long adminId) {
        Optional<Demande> optionalDemande = demandeRepository.findById(id);
        Administrateur admin = administrateurRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        if (optionalDemande.isPresent()) {
            Demande demande = optionalDemande.get();
            demande.setDateTraitement(new Date());
            demande.setStatut(StatutDemande.EN_COURS);
            demande.setAdmin(admin);

            HistoriqueDecisions historique = new HistoriqueDecisions();
            historique.setDemande(demande);
            historique.setAdmin(admin);
            historique.setStatutDecision(StatutDemande.EN_COURS);
            historique.setMessage(null);
            historiqueDecisionsRepository.save(historique);

            return demandeRepository.save(demande);

        }
        throw new RuntimeException("Demande non trouvée !");
    }



    public Demande traiterDemande(Long demandeId, Long adminId, String decision, String message) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));
        Administrateur admin = administrateurRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin non trouvé"));

        StatutDemande statutEnum;

        switch (decision.toUpperCase()) {
            case "ACCEPTEE":
                statutEnum = StatutDemande.ACCEPTEE;
                break;
            case "REFUSEE":
                statutEnum = StatutDemande.REFUSEE;
                break;
            case "EN_ATTENTE_JUSTIFICATIFS":
                statutEnum = StatutDemande.EN_ATTENTE_JUSTIFICATIFS;
                break;
            default:
                throw new IllegalArgumentException("Décision invalide !");
        }

        demande.setStatut(statutEnum);
        demande.setDateTraitement(new Date());
        demande.setMessageDecision(message);
        demande.setAdmin(admin);
        demandeRepository.save(demande);

        HistoriqueDecisions historique = new HistoriqueDecisions();
        historique.setDemande(demande);
        historique.setAdmin(admin);
        historique.setStatutDecision(statutEnum);
        historique.setMessage(message);
        historiqueDecisionsRepository.save(historique);

        envoyerNotification(demande.getEtudiant(), demande);

        return demande;
    }
    

    private void envoyerNotification(Etudiant etudiant, Demande demande) {
        Notification notification = new Notification();
        notification.setEtudiant(etudiant);
        notification.setMessage("Votre demande (ID: " + demande.getId() + ") a été mise à jour. Veuillez consulter les résultats de votre demande.");
        notificationRepository.save(notification);
    }

    public List<Demande> findByStatut(StatutDemande  statut) {
        return demandeRepository.findByStatut(statut);
    }

    public List<Demande> findByEtudiantId(Long etudiantId) {
        return demandeRepository.findByEtudiantId(etudiantId);
    }

    public List<Demande> findByStatutAndEtudiantId(StatutDemande  statut, Long etudiantId) {
        return demandeRepository.findByStatutAndEtudiantId(statut, etudiantId);
    }

}


