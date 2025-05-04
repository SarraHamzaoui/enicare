package com.sarliftou.enicare.repositories;

import com.sarliftou.enicare.entities.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    Administrateur findByEmail(String email);

}
