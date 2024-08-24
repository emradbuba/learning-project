package com.gitlab.emradbuba.learning.learningproject.config.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.EventProducerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.*;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventProducerConfig {

    private final EventProducerFactory eventProducerFactory;

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
    public EventProducer amqStandardEventProducer() {
        EventProducerSettings eventProducerSettings = EventProducerSettings.builder()
                .producerName(producerName)
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName("CompanyEventsPeerToPeerQueue")
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PEER_TO_PEER)
                        .build())
                .build();

        return eventProducerFactory.createEventProducer(eventProducerSettings);
    }
}
