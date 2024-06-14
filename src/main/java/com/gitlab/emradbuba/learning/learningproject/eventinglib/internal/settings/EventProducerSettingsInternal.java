package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.validation.EventProducerSettingsValidator;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModel;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import lombok.Getter;

import static org.apache.commons.lang3.StringUtils.trim;

@Getter
public class EventProducerSettingsInternal {

    private final String producerName;
    private final String brokerName;
    private final String brokerUrl;
    private final String brokerUsername;
    private final String brokerPassword;
    private final String destinationName;
    private final EventCommunicationModel eventCommunicationModel;

    public EventProducerSettingsInternal(final EventProducerSettings eventProducerSettings) {
        EventProducerSettingsValidator.validateIncomingSettings(eventProducerSettings);

        this.brokerName = trim(eventProducerSettings.getEventBrokerSettings().getBrokerName());
        this.brokerUrl = trim(eventProducerSettings.getEventBrokerSettings().getBrokerUrl());
        this.brokerUsername = trim(eventProducerSettings.getEventBrokerSettings().getBrokerUsername());
        this.brokerPassword = trim(eventProducerSettings.getEventBrokerSettings().getBrokerPassword());
        this.producerName = trim(eventProducerSettings.getProducerName());
        this.destinationName = trim(eventProducerSettings.getEventProducerDestinationSettings().getDestinationName());
        this.eventCommunicationModel = eventProducerSettings.getEventProducerDestinationSettings().getEventCommunicationModel();
    }
}
