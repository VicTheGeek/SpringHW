package com.vicsergeev.notification.kafka;

import com.vicsergeev.notification.dto.UserEventDTO;
import com.vicsergeev.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserEventsListener {
    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(UserEventsListener.class);

    public UserEventsListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-service")
    public void onUserEvent(UserEventDTO event) {
        log.info("Received user event: operation={}, email={}, name={}", event.operation(), event.email(), event.name());
        if (UserEventDTO.CREATE.equals(event.operation())) {
            emailService.sendAccountCreated(event.email());
            log.info("Sent account created email to {}", event.email());
        } else if (UserEventDTO.DELETE.equals(event.operation())) {
            emailService.sendAccountDeleted(event.email());
            log.info("Sent account deleted email to {}", event.email());
        } else {
            log.info("Ignored event with operation {}", event.operation());
        }
    }
}


