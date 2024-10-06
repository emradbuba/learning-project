package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.message;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.other.LearningProjectUnexpectedException;
import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQSession;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;

import java.time.LocalDateTime;

@Slf4j
public class ActiveMQMessageConverter {

    public static ActiveMQTextMessage toActiveMQMessage(LearningAppMessage learningAppMessage, ActiveMQSession activeMQSession) {
        try {
            ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) activeMQSession.createTextMessage();
            activeMQTextMessage.setText(learningAppMessage.getMessageContent());

            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_ID, learningAppMessage.getMessageId());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TYPE, learningAppMessage.getMessageType());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TRIGGER, learningAppMessage.getMessageTrigger());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENDER_USER, learningAppMessage.getMessageSender());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENDER_APP, learningAppMessage.getMessageSenderApp());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_CREATED_DATETIME, learningAppMessage.getCreatedDateTime().toString());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENT_DATETIME, LocalDateTime.now().toString());

            return activeMQTextMessage;

        } catch (JMSException e) {
            log.warn("Could not convert message {} to ActiveMQMessage - '{}'", learningAppMessage.getMessageId(), e.getMessage());
            throw new LearningProjectUnexpectedException("Could not convert message to ActiveMQMessage - won't be sent ==> " + learningAppMessage.getMessageId());
        }
    }

    public static LearningAppMessage fromActiveMQTextMessage(ActiveMQTextMessage activeMQTextMessage) {
        try {
            return LearningAppMessage.builder()
                    .messageId(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_ID))
                    .messageContent(activeMQTextMessage.getText())
                    .messageType(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TYPE))
                    .messageTrigger(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TRIGGER))
                    .messageSender(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENDER_USER))
                    .messageSenderApp(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENDER_APP))
                    .createdDateTime(LocalDateTime.parse(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_CREATED_DATETIME)))
                    .build();

        } catch (JMSException e) {
            throw new LearningProjectUnexpectedException("Could not convert activeMQTextMessage to learningMsg - won't be consumer (?)"); // TODO: log sth reasonable to identify activeMQTextMessage
        }
    }
}
