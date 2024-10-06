package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.message;

public final class ActiveMQMessageProperties {

    private ActiveMQMessageProperties() {
    }

    public static final String PROPERTY_MESSAGE_ID = "messageId";
    public static final String PROPERTY_MESSAGE_TYPE = "messageType";
    public static final String PROPERTY_MESSAGE_TRIGGER = "messageTrigger";
    public static final String PROPERTY_MESSAGE_SENDER_USER = "sender_user";
    public static final String PROPERTY_MESSAGE_SENDER_APP = "sender_app";
    public static final String PROPERTY_MESSAGE_CREATED_DATETIME = "created";
    public static final String PROPERTY_MESSAGE_SENT_DATETIME = "sent";
}
