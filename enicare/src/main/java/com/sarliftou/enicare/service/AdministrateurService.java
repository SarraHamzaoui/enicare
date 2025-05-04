package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.Administrateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sarliftou.enicare.repositories.AdministrateurRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdministrateurService {
    private final AdministrateurRepository administrateurRepository;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@enicar\\.ucar\\.tn$";
    private final EmailService emailService;

    public List<Administrateur> getAllAdministrateur(){
        return administrateurRepository.findAll();
    }

    public Optional<Administrateur> getAdministrateurById(Long id){
        return administrateurRepository.findById(id);
    }

    public Administrateur saveAdministrateur(Administrateur administrateur) {
        if (!isEmailValide(administrateur.getEmail())) {
            throw new IllegalArgumentException("L'email doit être institutionnel (@enicar.ucar.tn)");
        }

        String code = emailService.generateVerificationCode(administrateur);

        administrateur.setVerificationCode(code);
        administrateur.setVerified(false);

        Administrateur savedAdministrateur = administrateurRepository.save(administrateur);

        emailService.sendVerificationCode(administrateur.getEmail(), code);

        return savedAdministrateur;
    }

    private boolean isEmailValide(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public boolean verifyCode(String email, String code) {
        Administrateur administrateur = administrateurRepository.findByEmail(email);

        if (administrateur != null && administrateur.getVerificationCode() != null) {
            if (administrateur.getVerificationCode().equals(code)) {
                if (administrateur.getExpirationDate() != null && administrateur.getExpirationDate().after(new Date())) {
                    administrateur.setVerified(true);
                    administrateur.setVerificationCode(null);
                    administrateur.setExpirationDate(null);
                    administrateurRepository.save(administrateur);
                    return true;
                } else {
                    administrateur.setVerificationCode(null);
                    administrateur.setExpirationDate(null);
                    administrateurRepository.save(administrateur);
                }
            }
        }
        return false;
    }

    public void deleteAdministrateur(Long id) {
        administrateurRepository.deleteById(id);
    }

}
