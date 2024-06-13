package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.producer.EventProducerSettings;

public final class EventProducerFactory {
    private EventProducerFactory() {
        // Do not create instances
    }

    public static EventProducer createEventProducer(EventProducerSettings eventProducerSettings) {
        throw new UnsupportedOperationException("This operation is not yet implemented");
    }
}
