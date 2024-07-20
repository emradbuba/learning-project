package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.ActiveMQEventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;

public final class EventConsumerFactory {
    private EventConsumerFactory() {
        // Do not create instances
    }

    public static EventConsumer createEventConsumer(final EventConsumerSettings eventConsumerSettings) {
        EventConsumerSettingsCore eventConsumerSettingsCore = new EventConsumerSettingsCore(eventConsumerSettings);

        return createConcreteConsumer(eventConsumerSettingsCore);
    }

    private static EventConsumer createConcreteConsumer(final EventConsumerSettingsCore eventConsumerSettingsCore) {

        EventBrokerType eventBrokerType = eventConsumerSettingsCore.getEventBrokerType();
        if (eventBrokerType == EventBrokerType.ACTIVE_MQ) {
            return new ActiveMQEventConsumer(eventConsumerSettingsCore);
        }
        throw new IllegalStateException("Cannot create an event consumer - unsupported eventBrokerType: " + eventBrokerType);
    }
}
