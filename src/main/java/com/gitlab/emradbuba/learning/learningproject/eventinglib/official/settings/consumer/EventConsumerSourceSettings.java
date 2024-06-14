package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventConsumerSourceSettings {
    private final String sourceName;
    private final EventCommunicationModel eventCommunicationModel;
}
