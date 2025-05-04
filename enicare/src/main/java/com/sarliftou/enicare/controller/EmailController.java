package com.sarliftou.enicare.controller;

import com.sarliftou.enicare.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/send_email")
    public String sendTestEmail(@RequestParam String email) {
        emailService.sendEmail(email, "Test SMTP", "Ceci est un e-mail de test !");
        return "E-mail envoyé à " + email;
    }
}
