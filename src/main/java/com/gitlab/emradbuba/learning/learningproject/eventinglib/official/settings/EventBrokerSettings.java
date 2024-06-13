package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventBrokerSettings {
    private final String brokerName;
    private final String brokerUrl;
    private final String brokerUsername;
    private final String brokerPassword;
    private final EventBrokerType eventBrokerType;
}
