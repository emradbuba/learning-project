package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.validation.EventProducerSettingsValidator;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModel;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import lombok.Getter;
import org.springframework.util.StringUtils;

import static com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.ActiveMQEventProducer.AMQ_VIRTUAL_TOPIC_PREFIX;
import static org.apache.commons.lang3.StringUtils.trim;

@Getter
public class EventProducerSettingsCore {

    private final String producerName;
    private final String brokerName;
    private final String brokerUrl;
    private final String brokerUsername;
    private final String brokerPassword;
    private final String destinationName;
    private final EventBrokerType eventBrokerType;
    private final EventCommunicationModel eventCommunicationModel;

    public EventProducerSettingsCore(final EventProducerSettings eventProducerSettings) {
        EventProducerSettingsValidator.validateIncomingSettings(eventProducerSettings);

        this.brokerName = trim(eventProducerSettings.getEventBrokerSettings().getBrokerName());
        this.brokerUrl = trim(eventProducerSettings.getEventBrokerSettings().getBrokerUrl());
        this.brokerUsername = trim(eventProducerSettings.getEventBrokerSettings().getBrokerUsername());
        this.brokerPassword = trim(eventProducerSettings.getEventBrokerSettings().getBrokerPassword());
        this.producerName = trim(eventProducerSettings.getProducerName());
        this.destinationName = normalizeDestinationNameAgainstAmqVirtualTopic(eventProducerSettings); // TODO: AMQ-specific part in abstract code!
        this.eventBrokerType = eventProducerSettings.getEventBrokerSettings().getEventBrokerType();
        this.eventCommunicationModel = eventProducerSettings.getEventProducerDestinationSettings().getEventCommunicationModel();
    }

    private static String normalizeDestinationNameAgainstAmqVirtualTopic(EventProducerSettings eventProducerSettings) {
        final String originalName = eventProducerSettings.getEventProducerDestinationSettings().getDestinationName().trim();
        return StringUtils.startsWithIgnoreCase(originalName, AMQ_VIRTUAL_TOPIC_PREFIX)
                ? originalName.substring(AMQ_VIRTUAL_TOPIC_PREFIX.length())
                : originalName;
    }
}
