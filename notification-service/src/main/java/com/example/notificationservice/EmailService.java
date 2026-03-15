package com.example.notificationservice;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String operation) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("no-reply@mysite.com");

        if ("CREATE".equals(operation)) {
            message.setSubject("Добро пожаловать!");
            message.setText("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        } else if ("DELETE".equals(operation)) {
            message.setSubject("Аккаунт удален");
            message.setText("Здравствуйте! Ваш аккаунт был удалён.");
        }

        mailSender.send(message);
    }
}
