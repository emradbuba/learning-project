package com.gitlab.emradbuba.learning.learningproject.service.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.LoggingEventUtils;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.message.ActiveMQMessageConverter;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.processing.IncomingMessageProcessor;
import jakarta.jms.Message;
import org.apache.activemq.artemis.jms.client.ActiveMQMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.springframework.stereotype.Component;

@Component
public class DefaultIncomingMessageProcessor implements IncomingMessageProcessor {

    @Override
    public void processMessage(Message incomingMessage, EventConsumer consumer) {

        switch (incomingMessage) {
            case ActiveMQTextMessage activeMQTextMessage -> {
                LearningAppMessage incomingLearningAppMessage = ActiveMQMessageConverter.fromActiveMQTextMessage(activeMQTextMessage);
                throwIfError(incomingLearningAppMessage);
                LoggingEventUtils.logIncomingEvent(consumer, incomingLearningAppMessage);
            }
            case ActiveMQMessage activeMQMessage -> {}
            default -> {}
        }
    }

    public void throwIfError(LearningAppMessage learningAppMessage) {
        // Fake "poison" message for testing - such messages should be redelivered according to broker's settings
        if ("Poison message".equalsIgnoreCase(learningAppMessage.getMessageContent())) {
            throw new RuntimeException("Cannot process this message - it causes exception");
        }
    }
}
