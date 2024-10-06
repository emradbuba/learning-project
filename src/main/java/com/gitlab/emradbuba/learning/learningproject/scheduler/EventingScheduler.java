package com.gitlab.emradbuba.learning.learningproject.scheduler;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@ConditionalOnProperty(value = "eventing.scheduled.producer.enabled", matchIfMissing = false)
public class EventingScheduler {

    private final EventProducer eventProducer;

    public EventingScheduler(@Qualifier("scheduledPeerProducer") EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @Scheduled(fixedDelayString = "${eventing.scheduled.producer.interval.ms}", initialDelay = 10000)
    public void sentRandomPeerEvent() {
        eventProducer.sendMessage(LearningAppMessage.builder()
                .messageId(UUID.randomUUID().toString())
                .messageContent("This is a sample fake content from scheduled event producer")
                .messageSenderApp("SchedulerApp")
                .messageSender("fake_user")
                .createdDateTime(LocalDateTime.now())
                .messageType("UPDATE")
                .messageTrigger("AutoScheduler Run")
                .build());
    }
}
