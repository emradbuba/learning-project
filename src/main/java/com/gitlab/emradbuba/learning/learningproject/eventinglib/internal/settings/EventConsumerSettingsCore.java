package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.validation.EventConsumerSettingsValidator;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.trim;

@Getter
public class EventConsumerSettingsCore {

    private final String microServiceName;
    private final String uniqueConsumerName;
    private final String brokerName;
    private final String brokerUrl;
    private final String brokerUsername;
    private final String brokerPassword;
    private final String sourceName;
    private final EventBrokerType eventBrokerType;
    private final EventCommunicationModelType eventCommunicationModelType;

    public EventConsumerSettingsCore(final EventConsumerSettings eventConsumerSettings) {
        EventConsumerSettingsValidator.validateIncomingSettings(eventConsumerSettings);

        this.microServiceName = trim(eventConsumerSettings.getMicroServiceName());
        this.uniqueConsumerName = trim(eventConsumerSettings.getConsumerName());
        this.brokerName = trim(eventConsumerSettings.getEventBrokerSettings().getBrokerName());
        this.brokerUrl = trim(eventConsumerSettings.getEventBrokerSettings().getBrokerUrl());
        this.brokerUsername = trim(eventConsumerSettings.getEventBrokerSettings().getBrokerUsername());
        this.brokerPassword = trim(eventConsumerSettings.getEventBrokerSettings().getBrokerPassword());
        this.eventBrokerType = eventConsumerSettings.getEventBrokerSettings().getEventBrokerType();
        this.sourceName = trim(eventConsumerSettings.getEventConsumerSourceSettings().getMessageSourceName());
        this.eventCommunicationModelType = eventConsumerSettings.getEventConsumerSourceSettings().getEventCommunicationModelType();
    }
}
