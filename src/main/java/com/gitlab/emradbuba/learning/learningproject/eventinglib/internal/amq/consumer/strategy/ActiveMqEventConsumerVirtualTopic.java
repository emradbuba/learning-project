package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.AbstractActiveMQEventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;

import static com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.ActiveMQEventProducer.AMQ_VIRTUAL_TOPIC_PREFIX;

@Slf4j
public class ActiveMqEventConsumerVirtualTopic extends AbstractActiveMQEventConsumer {

    public ActiveMqEventConsumerVirtualTopic(EventConsumerSettingsCore eventConsumerSettingsCore) {
        super(eventConsumerSettingsCore);
    }

    @Override
    public void createMessageConsumer() throws JMSException {
        /*
          In case of virtual topic we create a consumer connecting to a queue with defined name
          Consumer.[consumerName].VirtualTopic.[pureTopicName]
         */

        String pureTopicName = eventConsumerSettingsCore.getEventDestinationName();
        String virtualTopicName = AMQ_VIRTUAL_TOPIC_PREFIX + pureTopicName;
        String virtualTopicConsumerQueueName = "Consumer." + uniqueConsumerName + "." + virtualTopicName;

        log.info("EventConsumer '{}': Creating consumer connecting to VirtualTopic consuming queue '{}'...", uniqueConsumerName, virtualTopicConsumerQueueName);

        Queue virtualTopicConsumerQueue = session.createQueue(virtualTopicConsumerQueueName);
        consumer = session.createConsumer(virtualTopicConsumerQueue);
    }
}
