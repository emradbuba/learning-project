package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.validation.EventProducerSettingsValidator;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.trim;

@Getter
public class EventProducerSettingsCore {

    private final String producerName;
    private final String microServiceName;
    private final String brokerName;
    private final String brokerUrl;
    private final String brokerUsername;
    private final String brokerPassword;
    private final String destinationName;
    private final EventBrokerType eventBrokerType;
    private final EventCommunicationModelType eventCommunicationModelType;

    public EventProducerSettingsCore(final EventProducerSettings eventProducerSettings) {
        EventProducerSettingsValidator.validateIncomingSettings(eventProducerSettings);

        this.brokerName = trim(eventProducerSettings.getEventBrokerSettings().getBrokerName());
        this.brokerUrl = trim(eventProducerSettings.getEventBrokerSettings().getBrokerUrl());
        this.brokerUsername = trim(eventProducerSettings.getEventBrokerSettings().getBrokerUsername());
        this.brokerPassword = trim(eventProducerSettings.getEventBrokerSettings().getBrokerPassword());
        this.eventBrokerType = eventProducerSettings.getEventBrokerSettings().getEventBrokerType();
        this.producerName = trim(eventProducerSettings.getProducerName());
        this.eventCommunicationModelType = eventProducerSettings.getEventDestinationSettings().getEventCommunicationModelType();
        this.microServiceName = trim(eventProducerSettings.getMicroServiceName());
        this.destinationName = trim(eventProducerSettings.getEventDestinationSettings().getSourceName());
    }
}
