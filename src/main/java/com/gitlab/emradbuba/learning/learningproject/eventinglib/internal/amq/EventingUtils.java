package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq;

public final class EventingUtils {

    private EventingUtils() {
        // no instance...
    }

    public static final String CONSUMER_CONNECTION_CLIENT_ID_PREFIX = "CLIENT_C_";
    public static final String PRODUCER_CONNECTION_CLIENT_ID_PREFIX = "CLIENT_P_";

    public static final String AMQ_VIRTUAL_TOPIC_PREFIX = "VirtualTopic.";
}
