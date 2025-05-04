package com.sarliftou.enicare.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class HistoriqueDecisions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    @JsonIgnore
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    @JsonIgnore
    private Administrateur admin;

    @Enumerated(EnumType.STRING)
    private StatutDemande statutDecision;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateDecision;
    @PrePersist
    protected void onCreate() {
        this.dateDecision = new Date();

    }
}
