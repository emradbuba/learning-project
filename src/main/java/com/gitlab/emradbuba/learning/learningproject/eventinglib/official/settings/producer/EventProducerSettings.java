package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventDestinationSettings;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventProducerSettings {
    private final String producerName;
    private final EventBrokerSettings eventBrokerSettings;
    private final EventDestinationSettings eventDestinationSettings;
}
