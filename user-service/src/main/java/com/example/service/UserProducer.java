package com.example.service;

import com.example.dto.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public UserProducer(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String email, String operation) {
        kafkaTemplate.send(topic, new UserEvent(email, operation));
    }
}
