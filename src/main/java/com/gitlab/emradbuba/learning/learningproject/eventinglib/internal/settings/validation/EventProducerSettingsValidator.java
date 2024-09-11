package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.validation;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventDestinationSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.UrlUtils;

import java.util.Optional;

public class EventProducerSettingsValidator {

    public static void validateIncomingSettings(final EventProducerSettings eventProducerSettings) {
        validateSettingsNotNull(eventProducerSettings);
        validateBrokerName(eventProducerSettings);
        validateBrokerSettings(eventProducerSettings);
        validateDestinationSettings(eventProducerSettings);
    }

    private static void validateSettingsNotNull(final EventProducerSettings eventProducerSettings) {
        if (eventProducerSettings == null) {
            throw new IllegalStateException("EventProducerSettings are null - check your configuration");
        }
    }

    private static void validateBrokerName(final EventProducerSettings eventProducerSettings) {
        String producerName = eventProducerSettings.getProducerName();
        validateIfStringDefined(producerName, "producerName", producerName);
    }

    private static void validateBrokerSettings(final EventProducerSettings eventProducerSettings) {
        final String producerName = eventProducerSettings.getProducerName();
        final EventBrokerSettings brokerSettings = eventProducerSettings.getEventBrokerSettings();
        final String brokerUrl = brokerSettings.getBrokerUrl();
        final String brokerName = brokerSettings.getBrokerName();
        final String brokerUsername = brokerSettings.getBrokerUsername();
        final String brokerPassword = brokerSettings.getBrokerPassword();
        final EventBrokerType eventBrokerType = brokerSettings.getEventBrokerType();

        validateBrokerUrl(brokerUrl, producerName);
        validateEventBrokerType(eventBrokerType, producerName);
        validateIfStringDefined(brokerName, "brokerName", producerName);
        validateIfStringDefined(brokerUsername, "brokerUsername", producerName);
        validateIfStringDefined(brokerPassword, "brokerPassword", producerName);
    }

    private static void validateEventBrokerType(EventBrokerType eventBrokerType, String producerName) {
        if (eventBrokerType == null) {
            throw new IllegalArgumentException(
                    String.format("Incorrect settings of the '%s' event producer - eventBrokerType cannot be null", producerName));
        }
    }

    private static void validateDestinationSettings(final EventProducerSettings eventProducerSettings) {
        final String producerName = eventProducerSettings.getProducerName();
        final EventDestinationSettings eventDestinationSettings = eventProducerSettings.getEventDestinationSettings();
        final String destinationName = eventDestinationSettings.getSourceName();
        final EventCommunicationModelType communicationModel = eventDestinationSettings.getEventCommunicationModelType();

        validateIfStringDefined(destinationName, "destinationName", producerName);
        validateIfCommunicationModelDefined(communicationModel, producerName);
    }

    private static void validateIfStringDefined(String value, String settingName, String producerName) {
        Optional.ofNullable(value)
                .map(StringUtils::trim)
                .filter(StringUtils::isNoneBlank)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Incorrect settings of the '%s' event producer - the '%s' value is null or blank", producerName, settingName)));
    }

    private static void validateBrokerUrl(String urlString, String producerName) {
        validateIfStringDefined(urlString, "brokerUrl", producerName);
        if (!UrlUtils.isAbsoluteUrl(urlString)) {
            throw new IllegalArgumentException(
                    String.format("Incorrect brokerUrl for '%s' event producer - check your settings", producerName));
        }
    }

    private static void validateIfCommunicationModelDefined(EventCommunicationModelType communicationModel, String producerName) {
        if (communicationModel == null) {
            throw new IllegalArgumentException(
                    String.format("Incorrect settings of the '%s' event producer - communicationModel cannot be null", producerName));
        }
    }
}
