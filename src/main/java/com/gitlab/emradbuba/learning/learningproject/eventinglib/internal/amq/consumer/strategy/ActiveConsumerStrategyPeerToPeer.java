package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.AbstractActiveMQEventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveConsumerStrategyPeerToPeer extends AbstractActiveMQEventConsumer {

    public ActiveConsumerStrategyPeerToPeer(EventConsumerSettingsCore eventConsumerSettingsCore) {
        super(eventConsumerSettingsCore);
    }

    @Override
    public void createMessageConsumer() throws JMSException {
        /*
          In peer to peer approach, we just subscribe to a Queue
          without any specific subscription name:
        */
        Queue queue = session.createQueue(eventConsumerSettingsCore.getEventDestinationName());
        log.info("EventConsumer '{}': - creating consumer of the AMQ Queue '{}'...", uniqueConsumerName, queue.getQueueName());

        consumer = session.createConsumer(queue);
    }
}
