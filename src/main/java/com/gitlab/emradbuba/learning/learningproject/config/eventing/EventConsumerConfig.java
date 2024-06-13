package com.gitlab.emradbuba.learning.learningproject.config.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventingApproachType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerQueueSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConsumerConfig {

    @Bean
    public EventConsumer amqEventConsumer() {
        EventConsumerSettings.builder()
                .consumerName("")
                .eventBrokerSettings(EventBrokerSettings.builder()
                        .brokerName("")
                        .brokerUrl("")
                        .brokerUsername("")
                        .brokerPassword("")
                        .eventBrokerType(EventBrokerType.ACTIVE_MQ)
                        .build())
                .eventConsumerQueueSettings(EventConsumerQueueSettings.builder()
                        .sourceName("CommonEvents")
                        .eventingApproachType(EventingApproachType.PUBLISHER_SUBSCRIBER)
                        .build())
                .build();
    }

}
