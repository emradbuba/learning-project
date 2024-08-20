package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventDestinationSettings {
    private final String destinationName;
    private final EventCommunicationModelType eventCommunicationModelType;
}
