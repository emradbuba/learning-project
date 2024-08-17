package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.factory.AmqEventConsumerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class EventConsumerFactory {

    private final AmqEventConsumerFactory amqEventConsumerFactory;

    public EventConsumer createEventConsumer(final EventConsumerSettings eventConsumerSettings) {

        EventConsumerSettingsCore eventConsumerSettingsCore = new EventConsumerSettingsCore(eventConsumerSettings);

        return createConcreteConsumer(eventConsumerSettingsCore);
    }

    private EventConsumer createConcreteConsumer(final EventConsumerSettingsCore eventConsumerSettingsCore) {

        EventBrokerType eventBrokerType = eventConsumerSettingsCore.getEventBrokerType();
        if (eventBrokerType == EventBrokerType.ACTIVE_MQ) {
            return amqEventConsumerFactory.createEventConsumer(eventConsumerSettingsCore);
        }

        throw new IllegalStateException("Cannot create an event consumer - unsupported eventBrokerType: " + eventBrokerType);
    }
}
