package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventingApproachType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventProducerDestinationSettings {
    private final String destinationName;
    private final EventingApproachType eventingApproachType;
}
