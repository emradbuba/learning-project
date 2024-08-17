package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveMqEventConsumerVirtualTopic extends AbstractActiveMQEventConsumer {

    public ActiveMqEventConsumerVirtualTopic(EventConsumerSettingsCore eventConsumerSettingsCore) {
        super(eventConsumerSettingsCore);
    }

    @Override
    protected MessageConsumer createMessageConsumer() throws JMSException {

        throw new UnsupportedOperationException("This operation is not yet implemented");

        /*
        String topicName = eventConsumerSettingsCore.getSourceName();
        String virtualTopicName = AMQ_VIRTUAL_TOPIC_PREFIX + topicName;
        String virtualTopicConsumerQueueName = "Consumer." + uniqueConsumerName + "." + virtualTopicName;

        log.info("EventConsumer '{}': Creating VirtualTopic consumer queue '{}'...", uniqueConsumerName, virtualTopicConsumerQueueName);

        Queue virtualTopicConsumerQueue = session.createQueue(virtualTopicConsumerQueueName);
        return session.createConsumer(virtualTopicConsumerQueue);
        */
    }
}
