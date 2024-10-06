package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.autoconfig;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ConsumerAutoStart {

    private final List<EventConsumer> eventConsumerBeans;

    @EventListener(ApplicationStartedEvent.class)
    private void startConsumerInstances() {
        log.info("AutoStart of event consumers... Number of EventConsumers found: {}", eventConsumerBeans.size());
        this.eventConsumerBeans.stream()
                .filter(EventingLifecycleEntity.class::isInstance)
                .map(EventingLifecycleEntity.class::cast)
                .forEach(EventingLifecycleEntity::startEventingLifecycleEntity);
    }

    @PreDestroy
    private void stopConsumerInstances() {
        this.eventConsumerBeans.stream()
                .filter(EventingLifecycleEntity.class::isInstance)
                .map(EventingLifecycleEntity.class::cast)
                .forEach(EventingLifecycleEntity::stopEventingLifecycleEntity);
    }
}
