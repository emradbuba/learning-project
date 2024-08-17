package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.autoconfig;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle.EventingLifecycleEntity;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProducerAutoStart {

    private final List<EventProducer> eventProducerBeans;

    public ProducerAutoStart(List<EventProducer> eventProducerBeans) {
        this.eventProducerBeans = eventProducerBeans;
    }

    @EventListener(ApplicationStartedEvent.class)
    private void startProducerInstances() {
        log.info("AutoStart of event producers... Number of EventProducers found: {}", eventProducerBeans.size());
        this.eventProducerBeans.stream()
                .filter(EventingLifecycleEntity.class::isInstance)
                .map(EventingLifecycleEntity.class::cast)
                .forEach(EventingLifecycleEntity::startEventingLifecycleEntity);
    }

    @PreDestroy
    private void stopProducerInstances() {
        this.eventProducerBeans.stream()
                .filter(EventingLifecycleEntity.class::isInstance)
                .map(EventingLifecycleEntity.class::cast)
                .forEach(EventingLifecycleEntity::stopEventingLifecycleEntity);
    }
}
