package com.vicsergeev.notification.controller;

import com.vicsergeev.notification.service.EmailService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/created")
    public ResponseEntity<Void> notifyCreated(@RequestParam @Email String email) {
        emailService.sendAccountCreated(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/deleted")
    public ResponseEntity<Void> notifyDeleted(@RequestParam @Email String email) {
        emailService.sendAccountDeleted(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/custom")
    public ResponseEntity<Void> notifyCustom(@RequestParam @Email String email,
                                             @RequestParam @NotBlank String subject,
                                             @RequestParam @NotBlank String body) {
        emailService.send(email, subject, body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}


