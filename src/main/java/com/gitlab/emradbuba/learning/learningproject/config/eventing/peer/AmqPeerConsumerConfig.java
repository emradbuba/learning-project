package com.gitlab.emradbuba.learning.learningproject.config.eventing.peer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.EventConsumerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventDestinationSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmqPeerConsumerConfig {

    private final EventConsumerFactory eventConsumerFactory;

    @Value("${eventing.amq.broker.name}")
    private String amqBrokerName;
    @Value("${eventing.amq.broker.url}")
    private String amqBrokerUrl;
    @Value("${eventing.amq.broker.username}")
    private String amqBrokerUsername;
    @Value("${eventing.amq.broker.password}")
    private String amqBrokerPassword;
    @Value("${eventing.amq.peer.source.name}")
    private String amqPeerEventSourceName;

    @Bean
    public EventConsumer firstAppCreationAmqEventPeerToPeerConsumer() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceName("ConsumerApp1")
                .consumerName("eventsConsumerPeer1")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .sourceName(amqPeerEventSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PEER_TO_PEER)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

    @Bean
    public EventConsumer secondAppCreationAmqEventPeerToPeerConsumer() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceName("ConsumerApp2")
                .consumerName("eventsConsumerPeer2")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .sourceName(amqPeerEventSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PEER_TO_PEER)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }
}
