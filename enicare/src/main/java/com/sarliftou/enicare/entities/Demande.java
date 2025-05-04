package com.sarliftou.enicare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarliftou.enicare.repositories.AdministrateurRepository;
import com.sarliftou.enicare.repositories.HistoriqueDecisionsRepository;
import com.sarliftou.enicare.repositories.NotificationRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Administrateur admin;


    @Column(nullable = false, length = 150)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateSoumission;

    @Column(nullable = true)
    private Date dateTraitement;

    @PrePersist
    protected void onCreate() {
        this.dateSoumission = new Date();
        this.dateTraitement = null;
        this.statut = StatutDemande.EN_ATTENTE;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemande statut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategorieDemande categorie;

    private String autreCategorie;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String messageDecision;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Justificatif> justificatifs;


}
