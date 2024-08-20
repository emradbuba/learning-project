package com.gitlab.emradbuba.learning.learningproject.config.eventing;

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
public class EventConsumerConfig {

    private final EventConsumerFactory eventConsumerFactory;

    @Value("${eventing.amq.broker.name}")
    private String amqBrokerName;
    @Value("${eventing.amq.broker.url}")
    private String amqBrokerUrl;
    @Value("${eventing.amq.broker.username}")
    private String amqBrokerUsername;
    @Value("${eventing.amq.broker.password}")
    private String amqBrokerPassword;
    @Value("${eventing.amq.consumer.name}")
    private String consumerName;
    @Value("${eventing.amq.consumer.sourceName}")
    private String consumerSourceName;

    @Bean
    public EventConsumer amqEventConsumer1() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("App1")
                .consumerName("ChangeEventConsumer")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName("RejdiEventsVP")
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_VIRTUAL_TOPIC)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

    @Bean
    public EventConsumer amqEventConsumer2() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("App1")
                .consumerName("DeleteEventConsumer")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName("RejdiEventsVP")
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_VIRTUAL_TOPIC)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

    @Bean
    public EventConsumer amqEventConsumer3_POD1() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("App2")
                .consumerName("AllEventConsumer")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName("RejdiEventsVP")
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_VIRTUAL_TOPIC)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }


    @Bean
    public EventConsumer amqEventConsumer3_POD2() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("App2")
                .consumerName("AllEventConsumer")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName("RejdiEventsVP")
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_VIRTUAL_TOPIC)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

    @Bean
    public EventConsumer amqEventConsumer3_POD3() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .microServiceUniqueName("App2")
                .consumerName("AllEventConsumer")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventDestinationSettings(EventDestinationSettings.builder()
                        .destinationName("RejdiEventsVP")
                        .eventCommunicationModelType(EventCommunicationModelType.AMQ_VIRTUAL_TOPIC)
                        .build())
                .build();

        return eventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }



}
