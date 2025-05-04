package com.sarliftou.enicare.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Justificatif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = true)
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;
}
