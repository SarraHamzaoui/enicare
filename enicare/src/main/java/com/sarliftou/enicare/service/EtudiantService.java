package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.Etudiant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sarliftou.enicare.repositories.EtudiantRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EtudiantService {
    private final EtudiantRepository etudiantRepository;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@enicar\\.ucar\\.tn$";
    private final EmailService emailService;

    public List<Etudiant> getAllEtudiant(){
        return etudiantRepository.findAll();
    }

    public Optional<Etudiant> getEtudiantById(Long id){
        return etudiantRepository.findById(id);
    }

    public Etudiant saveEtudiant(Etudiant etudiant) {
        if (!isEmailValide(etudiant.getEmail())) {
            throw new IllegalArgumentException("L'email doit être institutionnel (@enicar.ucar.tn)");
        }

        String code = emailService.generateVerificationCode(etudiant);

        etudiant.setVerificationCode(code);
        etudiant.setVerified(false);

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        emailService.sendVerificationCode(etudiant.getEmail(), code);

        return savedEtudiant;
    }

    public boolean verifyCode(String email, String code) {
        Etudiant etudiant = etudiantRepository.findByEmail(email);

        if (etudiant != null && etudiant.getVerificationCode() != null) {
            if (etudiant.getVerificationCode().equals(code)) {
                if (etudiant.getExpirationDate() != null && etudiant.getExpirationDate().after(new Date())) {
                    etudiant.setVerified(true);
                    etudiant.setVerificationCode(null);
                    etudiant.setExpirationDate(null);
                    etudiantRepository.save(etudiant);
                    return true;
                } else {
                    etudiant.setVerificationCode(null);
                    etudiant.setExpirationDate(null);
                    etudiantRepository.save(etudiant);
                }
            }
        }
        return false;
    }

    private boolean isEmailValide(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

}
