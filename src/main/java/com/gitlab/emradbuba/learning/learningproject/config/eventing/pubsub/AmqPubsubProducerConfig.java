package com.gitlab.emradbuba.learning.learningproject.config.eventing.pubsub;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.EventProducerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventDestinationSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmqPubsubProducerConfig {

    private final EventProducerFactory eventProducerFactory;

    @Value("${eventing.amq.broker.name}")
    private String amqBrokerName;
    @Value("${eventing.amq.broker.url}")
    private String amqBrokerUrl;
    @Value("${eventing.amq.broker.username}")
    private String amqBrokerUsername;
    @Value("${eventing.amq.broker.password}")
    private String amqBrokerPassword;
    @Value("${eventing.amq.producer.pubsub.name}")
    private String producerName;
    @Value("${eventing.amq.producer.pubsub.destination.name}")
    private String producerDestinationName;

    @Bean(name = "amqPubsubProducer")
    public EventProducer amqPeerProducer() {
        EventProducerSettings eventProducerSettings = EventProducerSettings.builder()
                .producerName(producerName)
                .microServiceName("LearningApp")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName(producerDestinationName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PUBLISH_SUBSCRIBE)
                        .build())
                .build();

        return eventProducerFactory.createEventProducer(eventProducerSettings);
    }
}