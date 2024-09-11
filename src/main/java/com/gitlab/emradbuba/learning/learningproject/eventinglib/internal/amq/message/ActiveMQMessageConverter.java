package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.message;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppAmqMessage;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.other.LPUnexpectedException;
import jakarta.jms.JMSException;
import org.apache.activemq.artemis.jms.client.ActiveMQSession;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;

public class ActiveMQMessageConverter {

    public static ActiveMQTextMessage toActiveMQMessage(LearningAppAmqMessage learningAppAmqMessage, ActiveMQSession activeMQSession) {
        try {
            ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) activeMQSession.createTextMessage();
            activeMQTextMessage.setText(learningAppAmqMessage.getMessageContent());

            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_ID, learningAppAmqMessage.getMessageId());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TYPE, learningAppAmqMessage.getMessageType());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TRIGGER, learningAppAmqMessage.getMessageTrigger());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENDER_USER, learningAppAmqMessage.getMessageSendingUser());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENDER_APP, learningAppAmqMessage.getMessageSendingApplication());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_CREATED_DATETIME, learningAppAmqMessage.getCreatedDateTime().toString());
            activeMQTextMessage.setStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_SENT_DATETIME, learningAppAmqMessage.getSentDateTime().toString());

            return activeMQTextMessage;

        } catch (JMSException e) {
            throw new LPUnexpectedException("Could not convert message to ActiveMQMessage - won't be sent ==> " + learningAppAmqMessage.getMessageId());
        }
    }

    public static LearningAppAmqMessage fromActiveMQTextMessage(ActiveMQTextMessage activeMQTextMessage) {
        try {
            return LearningAppAmqMessage.builder()
                    .messageId(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_ID))
                    .messageContent(activeMQTextMessage.getText())
                    .messageTrigger(activeMQTextMessage.getStringProperty(ActiveMQMessageProperties.PROPERTY_MESSAGE_TRIGGER))
                    //.
                    //.  <--- handle other values...
                    //.
                    .build();

        } catch (JMSException e) {
            throw new LPUnexpectedException("Could not convert activeMQTextMessage to learningMsg - won't be consumer (?)"); // TODO: log sth reasonable to identify activeMQTextMessage
        }
    }
}
