package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.validation;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventDestinationSettings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.UrlUtils;

import java.util.Optional;

public class EventConsumerSettingsValidator {
    public static void validateIncomingSettings(final EventConsumerSettings eventConsumerSettings) {
        validateSettingsNotNull(eventConsumerSettings);
        validateClientId(eventConsumerSettings);
        validateBrokerName(eventConsumerSettings);
        validateIncomingMessageProcessor(eventConsumerSettings);
        validateBrokerSettings(eventConsumerSettings);
        validateSourceSettings(eventConsumerSettings);
    }

    private static void validateSettingsNotNull(final EventConsumerSettings eventConsumerSettings) {
        if (eventConsumerSettings == null) {
            throw new IllegalStateException("EventConsumerSettings are null - check your configuration");
        }
    }

    private static void validateClientId(final EventConsumerSettings eventConsumerSettings) {
        String clientId = eventConsumerSettings.getMicroServiceName();
        validateIfStringDefined(clientId, "clientId", clientId);
    }

    private static void validateBrokerName(final EventConsumerSettings eventConsumerSettings) {
        String consumerName = eventConsumerSettings.getConsumerName();
        validateIfStringDefined(consumerName, "consumerName", consumerName);
    }

    private static void validateIncomingMessageProcessor(final EventConsumerSettings eventConsumerSettings) {
        if(eventConsumerSettings.getIncomingMessageProcessor() == null) {
            throw new IllegalArgumentException("Missing incoming message processor - cannot create consumer...");
        }
    }

    private static void validateBrokerSettings(final EventConsumerSettings eventConsumerSettings) {
        final String consumerName = eventConsumerSettings.getConsumerName();
        final EventBrokerSettings brokerSettings = eventConsumerSettings.getEventBrokerSettings();
        final String brokerUrl = brokerSettings.getBrokerUrl();
        final String brokerName = brokerSettings.getBrokerName();
        final String brokerUsername = brokerSettings.getBrokerUsername();
        final String brokerPassword = brokerSettings.getBrokerPassword();
        final EventBrokerType eventBrokerType = brokerSettings.getEventBrokerType();

        validateBrokerUrl(brokerUrl, consumerName);
        validateEventBrokerType(eventBrokerType, consumerName);
        validateIfStringDefined(brokerName, "brokerName", consumerName);
        validateIfStringDefined(brokerUsername, "brokerUsername", consumerName);
        validateIfStringDefined(brokerPassword, "brokerPassword", consumerName);
    }

    private static void validateEventBrokerType(EventBrokerType eventBrokerType, String consumerName) {
        if (eventBrokerType == null) {
            throw new IllegalArgumentException(
                    String.format("Incorrect settings of the '%s' event consumer - eventBrokerType cannot be null", consumerName));
        }
    }

    private static void validateSourceSettings(final EventConsumerSettings eventConsumerSettings) {
        final String consumerName = eventConsumerSettings.getConsumerName();
        final EventDestinationSettings sourceSettings = eventConsumerSettings.getEventDestinationSettings();
        final String sourceName = sourceSettings.getSourceName();
        final EventCommunicationModelType communicationModel = sourceSettings.getEventCommunicationModelType();

        validateIfStringDefined(sourceName, "sourceName", consumerName);
        validateIfCommunicationModelDefined(communicationModel, consumerName);
    }

    private static void validateIfStringDefined(String value, String settingName, String consumerName) {
        Optional.ofNullable(value)
                .map(StringUtils::trim)
                .filter(StringUtils::isNoneBlank)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Incorrect settings of the '%s' event consumer - the '%s' value is null or blank", consumerName, settingName)));
    }

    private static void validateBrokerUrl(String urlString, String consumerName) {
        validateIfStringDefined(urlString, "brokerUrl", consumerName);
        if (!UrlUtils.isAbsoluteUrl(urlString)) {
            throw new IllegalArgumentException(
                    String.format("Incorrect brokerUrl for '%s' event consumer - check your settings", consumerName));
        }
    }

    private static void validateIfCommunicationModelDefined(EventCommunicationModelType communicationModel, String consumerName) {
        if (communicationModel == null) {
            throw new IllegalArgumentException(
                    String.format("Incorrect settings of the '%s' event consumer - communicationModel cannot be null", consumerName));
        }
    }
}
