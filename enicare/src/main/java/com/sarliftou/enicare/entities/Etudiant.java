package com.sarliftou.enicare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Etudiant extends Utilisateur {

    @Column(nullable = false, length = 100)
    private String filiere;

    @Column(nullable = false)
    private String carteEtudiantUrl;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Demande> demandes;

    @OneToMany(mappedBy = "etudiant")
    @JsonIgnore
    private List<Notification> notifications;

    public Etudiant() {
        super();
        this.setRole(Role.ETUDIANT);
    }
}
