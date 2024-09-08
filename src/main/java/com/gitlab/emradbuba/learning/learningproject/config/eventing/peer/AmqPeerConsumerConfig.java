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
    @Value("${eventing.amq.consumer.peer.first.name}")
    private String firstConsumerName;
    @Value("${eventing.amq.consumer.peer.first.source.name}")
    private String firstConsumerSourceName;
    @Value("${eventing.amq.consumer.peer.second.name}")
    private String secondConsumerName;
    @Value("${eventing.amq.consumer.peer.second.source.name}")
    private String secondConsumerSourceName;

    @Bean
    public EventConsumer firstAmqEventPeerToPeerConsumer() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("LearningApp")
                .consumerName(firstConsumerName)
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName(firstConsumerSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PEER_TO_PEER)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

    @Bean
    public EventConsumer secondAmqEventPeerToPeerConsumer() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("LearningApp")
                .consumerName(secondConsumerName)
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName(secondConsumerSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PEER_TO_PEER)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }
}
