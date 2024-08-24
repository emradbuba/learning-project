package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.AbstractActiveMQEventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveMQEventProducerPeerToPeer extends AbstractActiveMQEventProducer {

    public ActiveMQEventProducerPeerToPeer(EventProducerSettingsCore eventProducerSettingsCore) {
        super(eventProducerSettingsCore);
    }

    @Override
    protected void createMessageProducer() throws JMSException {
        /*
          In peer to peer approach, we just create a queue as a destination
        */
        Queue queue = session.createQueue(eventProducerSettingsCore.getDestinationName());

        log.info("EventProducer '{}': Creating queue '{}'...", producerName, queue.getQueueName());

        producer = session.createProducer(queue);

        // TODO: producers settings, durability, redelivery...
    }
}
