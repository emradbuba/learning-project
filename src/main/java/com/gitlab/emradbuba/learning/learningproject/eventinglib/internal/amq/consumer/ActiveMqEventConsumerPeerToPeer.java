package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveMqEventConsumerPeerToPeer extends AbstractActiveMQEventConsumer {

    public ActiveMqEventConsumerPeerToPeer(EventConsumerSettingsCore eventConsumerSettingsCore) {
        super(eventConsumerSettingsCore);
    }

    @Override
    protected MessageConsumer createMessageConsumer() throws JMSException {

        String queueName = eventConsumerSettingsCore.getSourceName();
        log.info("EventConsumer '{}': Creating AMQ message consumer using peer-to-peer queue '{}'...", uniqueConsumerName, queueName);
        Queue queue = session.createQueue(queueName);

        return session.createConsumer(queue);
    }
}
