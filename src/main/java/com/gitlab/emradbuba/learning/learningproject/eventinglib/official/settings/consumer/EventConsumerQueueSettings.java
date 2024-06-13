package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventingApproachType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventConsumerQueueSettings {
    private final String sourceName;
    private final EventingApproachType eventingApproachType;
}
