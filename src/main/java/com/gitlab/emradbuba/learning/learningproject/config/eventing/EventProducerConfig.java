package com.gitlab.emradbuba.learning.learningproject.config.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.EventProducerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.*;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerDestinationSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventProducerConfig {

    @Value("${eventing.amq.broker.name}")
    private String amqBrokerName;
    @Value("${eventing.amq.broker.url}")
    private String amqBrokerUrl;
    @Value("${eventing.amq.broker.username}")
    private String amqBrokerUsername;
    @Value("${eventing.amq.broker.password}")
    private String amqBrokerPassword;
    @Value("${eventing.amq.producer.name}")
    private String producerName;
    @Value("${eventing.amq.producer.destinationName}")
    private String producerDestinationName;

    @Bean
    public EventProducer amqEventProducer() {
        EventProducerSettings eventProducerSettings = EventProducerSettings.builder()
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .build())
                .eventProducerDestinationSettings(EventProducerDestinationSettings.builder()
                        .producerName(producerName)
                        .destinationName(producerDestinationName)
                        .eventingApproachType(EventingApproachType.PUBLISHER_SUBSCRIBER)
                        .build())
                .build();
        return EventProducerFactory.createEventProducer(eventProducerSettings);
    }
}
