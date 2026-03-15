package com.example.notificationservice.consumer;

import com.example.dto.UserEvent;
import com.example.notificationservice.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {

    private final EmailService emailService;

    public UserEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEvent event) {
        // Консьюмер не знает, ЧТО писать в письме, он просто просит EmailService отправить его
        emailService.sendEmail(event.email(), event.operation());
    }
}
