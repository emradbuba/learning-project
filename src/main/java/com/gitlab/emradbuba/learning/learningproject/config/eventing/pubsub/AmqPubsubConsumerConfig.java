package com.gitlab.emradbuba.learning.learningproject.config.eventing.pubsub;

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

//@Configuration
@RequiredArgsConstructor
public class AmqPubsubConsumerConfig {

    private final EventConsumerFactory eventConsumerFactory;

    @Value("${eventing.amq.broker.name}")
    private String amqBrokerName;
    @Value("${eventing.amq.broker.url}")
    private String amqBrokerUrl;
    @Value("${eventing.amq.broker.username}")
    private String amqBrokerUsername;
    @Value("${eventing.amq.broker.password}")
    private String amqBrokerPassword;
    @Value("${eventing.amq.consumer.pubsub.first.name}")
    private String firstConsumerName;
    @Value("${eventing.amq.consumer.pubsub.first.source.name}")
    private String firstConsumerSourceName;
    @Value("${eventing.amq.consumer.pubsub.second.name}")
    private String secondConsumerName;
    @Value("${eventing.amq.consumer.pubsub.second.source.name}")
    private String secondConsumerSourceName;

    @Bean
    public EventConsumer firstAmqEventPubsubConsumer() {
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
                        .sourceName(firstConsumerSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PUBLISH_SUBSCRIBE)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

    @Bean
    public EventConsumer secondAmqEventPubsubConsumer() {
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
                        .sourceName(secondConsumerSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_PUBLISH_SUBSCRIBE)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }
}
