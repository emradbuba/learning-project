package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsInternal;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;

public final class EventProducerFactory {

    private EventProducerFactory() {
        // Do not create instances
    }

    public static EventProducer createEventProducer(EventProducerSettings eventProducerSettings) {
        EventBrokerType brokerType = eventProducerSettings.getEventBrokerSettings().getEventBrokerType();
        EventProducerSettingsInternal eventProducerSettingsInternal = new EventProducerSettingsInternal(eventProducerSettings);

        return createConcreteProducer(eventProducerSettingsInternal, brokerType);
    }

    private static EventProducer createConcreteProducer(EventProducerSettingsInternal eventProducerSettingsInternal, EventBrokerType brokerType) {
        if (brokerType == EventBrokerType.ACTIVE_MQ) {
            return new ActiveMQEventProducer(eventProducerSettingsInternal);
        }
    }
}
