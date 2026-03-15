package com.example.notificationservice;

import com.example.dto.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendManual(@RequestBody UserEvent event) {
        try {
            emailService.sendEmail(event.email(), event.operation());
            return "Сообщение успешно отправлено на " + event.email();
        } catch (Exception e) {

            return "Ошибка при отправке почты: " + e.getMessage();
        }
    }
}

