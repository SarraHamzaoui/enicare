package com.sarliftou.enicare.repositories;

import com.sarliftou.enicare.entities.Etudiant;
import com.sarliftou.enicare.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    long countByRole(Role role);
    Etudiant findByEmail(String email);
}
