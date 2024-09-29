package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.processing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import jakarta.jms.Message;

public interface IncomingMessageProcessor {

    void processMessage(Message incomingMessage, EventConsumer consumer);
}
