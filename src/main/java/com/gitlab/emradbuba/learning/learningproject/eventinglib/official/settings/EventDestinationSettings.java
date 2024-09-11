package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventDestinationSettings { // TODO: Discuss the name
    private final String sourceName;
    private final EventCommunicationModelType eventCommunicationModelType;
}
