package com.sarliftou.enicare.service;

import com.sarliftou.enicare.entities.Administrateur;
import com.sarliftou.enicare.entities.Etudiant;
import com.sarliftou.enicare.entities.Utilisateur;
import com.sarliftou.enicare.repositories.AdministrateurRepository;
import com.sarliftou.enicare.repositories.EtudiantRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EtudiantRepository etudiantRepository;
    private final AdministrateurRepository administrateurRepository;

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("hamzaouisarra8@gmail.com");

        mailSender.send(mailMessage);
    }

    public String generateVerificationCode(Utilisateur utilisateur) {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        utilisateur.setVerificationCode(String.valueOf(code));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        utilisateur.setExpirationDate(calendar.getTime());

        if (utilisateur instanceof Etudiant) {
            etudiantRepository.save((Etudiant) utilisateur);
        } else if (utilisateur instanceof Administrateur) {
            administrateurRepository.save((Administrateur) utilisateur);
        }

        return utilisateur.getVerificationCode();
    }

    public void sendVerificationCode(String to, String code) {
        String subject = "Votre code de vérification";

        String htmlContent = "<!DOCTYPE html><html lang=\"fr\">"
                + "<head><meta charset=\"UTF-8\"><title>Vérification</title></head>"
                + "<body style=\"font-family: Arial, sans-serif; text-align: center; padding: 20px; background-color: #f4f4f4;\">"
                + "<div style=\"max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h2 style=\"color: #007bff;\">Code de Vérification</h2>"
                + "<p style=\"font-size: 16px;\">Bonjour,</p>"
                + "<p style=\"font-size: 16px;\">Votre code de vérification est :</p>"
                + "<h1 style=\"color: #007bff; background: #f0f0f0; padding: 10px; display: inline-block; border-radius: 5px;\">" + code + "</h1>"
                + "<p style=\"font-size: 14px;\">Ce code expirera dans <strong>1 heure</strong>.</p>"
                + "<p style=\"font-size: 14px;\">Si vous n'avez pas demandé ce code, veuillez ignorer cet email.</p>"
                + "<br><hr>"
                + "<p style=\"font-size: 12px; color: #777;\">&copy; 2025 - EniCare | Tous droits réservés.</p>"
                + "</div>"
                + "</body></html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.setFrom("hamzaouisarra8@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 86400000)
    public void removeExpiredCodes() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getExpirationDate() != null && etudiant.getExpirationDate().before(new Date())) {
                etudiant.setVerificationCode(null);
                etudiant.setExpirationDate(null);
                etudiantRepository.save(etudiant);
            }
        }

        List<Administrateur> administrateurs = administrateurRepository.findAll();
        for (Administrateur administrateur : administrateurs) {
            if (administrateur.getExpirationDate() != null && administrateur.getExpirationDate().before(new Date())) {
                administrateur.setVerificationCode(null);
                administrateur.setExpirationDate(null);
                administrateurRepository.save(administrateur);
            }
        }
    }

}
