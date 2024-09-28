package com.gitlab.emradbuba.learning.learningproject.config.eventing.peer;

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
public class AmqPeerProducerConfig {

    private final EventProducerFactory eventProducerFactory;

    @Value("${eventing.amq.broker.name}")
    private String amqBrokerName;
    @Value("${eventing.amq.broker.url}")
    private String amqBrokerUrl;
    @Value("${eventing.amq.broker.username}")
    private String amqBrokerUsername;
    @Value("${eventing.amq.broker.password}")
    private String amqBrokerPassword;
    @Value("${eventing.amq.peer.source.name}")
    private String amqPeerSourceName;

    // Come back here 5: define consumer using listeners

    @Bean(name = "amqPeerEventsProducer")
    public EventProducer amqPeerEventsProducer() {
        EventProducerSettings eventProducerSettings = EventProducerSettings.builder()
                .producerName("amqEventsProducerPeer")
                .microServiceName("LearningApplication")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .sourceName(amqPeerSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PEER_TO_PEER)
                        .build())
                .build();

        return eventProducerFactory.createEventProducer(eventProducerSettings);
    }
}
