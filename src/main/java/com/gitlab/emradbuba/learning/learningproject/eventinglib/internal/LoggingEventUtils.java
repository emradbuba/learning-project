package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingEventUtils {

    public static void logIncomingEvent(EventConsumer consumer, LearningAppMessage incomingLearningAppMessage) {
        log.info("\nConsumer '{}/{}' receivedMessage\n\t ID: {} \n\t Text: {}\n\t Type: {}\n\t Trigger: {}\n\t Sender: {}\n\t SenderApp: {} \n\t Created: {}",
                consumer.getApplicationName(), consumer.getName(),
                incomingLearningAppMessage.getMessageId(),
                incomingLearningAppMessage.getMessageContent(),
                incomingLearningAppMessage.getMessageType(),
                incomingLearningAppMessage.getMessageTrigger(),
                incomingLearningAppMessage.getMessageSender(),
                incomingLearningAppMessage.getMessageSenderApp(),
                incomingLearningAppMessage.getCreatedDateTime().toString()
        );
    }

    public static void logOutcomingEvent(EventProducer producer, LearningAppMessage learningAppMessage) {
        log.info("\n[EventProducer '{}'] Message sent" + "\n (ID={})" + "\n (bytes={})",
                producer.getName(), learningAppMessage.getMessageId(), learningAppMessage.getMessageContent().getBytes().length);
    }

}
