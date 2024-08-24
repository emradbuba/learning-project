package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.factory.AmqEventProducerFactory;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventBrokerType;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class EventProducerFactory {

    private final AmqEventProducerFactory amqEventProducerFactory;

    public EventProducer createEventProducer(final EventProducerSettings eventProducerSettings) {

        EventProducerSettingsCore eventProducerSettingsCore = new EventProducerSettingsCore(eventProducerSettings);

        return createConcreteProducer(eventProducerSettingsCore);
    }

    private EventProducer createConcreteProducer(EventProducerSettingsCore eventProducerSettingsCore) {

        EventBrokerType eventBrokerType = eventProducerSettingsCore.getEventBrokerType();
        if (eventBrokerType == EventBrokerType.ACTIVE_MQ) {
            return amqEventProducerFactory.createEventProducer(eventProducerSettingsCore);
        }

        throw new IllegalStateException("Cannot create an event producer - unsupported eventBrokerType: " + eventBrokerType);
    }
}
