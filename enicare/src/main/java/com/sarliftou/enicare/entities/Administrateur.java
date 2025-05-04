package com.sarliftou.enicare.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Administrateur extends Utilisateur{

    public Administrateur() {
        super();
        this.setRole(Role.ADMIN);
    }
}
