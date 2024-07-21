package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventProducerDestinationSettings {
    private final String messageDestinationName;
    private final EventCommunicationModelType eventCommunicationModelType;
}
