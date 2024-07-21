package com.gitlab.emradbuba.learning.learningproject.config.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.EventConsumerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSourceSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfig {

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
    public EventConsumer amqEventConsumer() {
        EventConsumerSettings eventConsumerSettings = EventConsumerSettings.builder()
                .consumerName(consumerName)
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName(amqBrokerName)
                        .brokerUrl(amqBrokerUrl)
                        .brokerUsername(amqBrokerUsername)
                        .brokerPassword(amqBrokerPassword)
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventConsumerSourceSettings(EventConsumerSourceSettings.builder()
                        .messageSourceName(consumerSourceName)
                        .eventCommunicationModelType(EventCommunicationModelType.VIA_VIRTUAL_TOPIC)
                        .build())
                .build();

        return EventConsumerFactory.createEventConsumer(eventConsumerSettings);
    }

}
