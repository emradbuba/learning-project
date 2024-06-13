package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.consumer.EventConsumerSettings;

public final class EventConsumerFactory {
    private EventConsumerFactory() {
        // Do not create instances
    }

    public static EventConsumer createEventConsumer(EventConsumerSettings eventConsumerSettings) {
        throw new UnsupportedOperationException("This operation is not yet implemented");
    }
}
