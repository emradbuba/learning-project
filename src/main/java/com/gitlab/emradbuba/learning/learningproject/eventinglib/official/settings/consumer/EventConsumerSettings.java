package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventDestinationSettings;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventConsumerSettings {
    private final String microServiceName;
    private final String consumerName;
    private final EventBrokerSettings eventBrokerSettings;
    private final EventDestinationSettings eventDestinationSettings;
}
