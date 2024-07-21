package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventConsumerSourceSettings {
    private final String messageSourceName;
    private final EventCommunicationModelType eventCommunicationModelType;
}
