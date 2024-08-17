package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.ActiveMQEventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;

public final class EventProducerFactory {

    private EventProducerFactory() {
        // Do not create instances
    }

    public static EventProducer createEventProducer(EventProducerSettings eventProducerSettings) {
        EventProducerSettingsCore eventProducerSettingsCore = new EventProducerSettingsCore(eventProducerSettings);
        return createConcreteProducer(eventProducerSettingsCore);
    }

    private static EventProducer createConcreteProducer(EventProducerSettingsCore eventProducerSettingsCore) {

        EventBrokerType eventBrokerType = eventProducerSettingsCore.getEventBrokerType();
        if (eventBrokerType == EventBrokerType.ACTIVE_MQ) {
            return new ActiveMQEventProducer(eventProducerSettingsCore);
        }
        throw new IllegalStateException("Cannot create an event producer - unsupported eventBrokerType: " + eventBrokerType);
    }
}
