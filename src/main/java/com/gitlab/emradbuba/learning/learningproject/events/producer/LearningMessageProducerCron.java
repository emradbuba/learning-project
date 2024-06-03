package com.gitlab.emradbuba.learning.learningproject.events.producer;

import jakarta.jms.JMSException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LearningMessageProducerCron {
    private final LearningMessageProducer learningMessageProducer;

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void sendMessages() {
        try {
            learningMessageProducer.produceMessage();
        } catch (JMSException e) {
            System.err.println("Could not send message: " + e.getMessage());
        }
    }
}
