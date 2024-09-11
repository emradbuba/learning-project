package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.AbstractActiveMQEventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQMessageProducer;

@Slf4j
public class ActiveMQEventProducerPublishSubscribe extends AbstractActiveMQEventProducer {

    public ActiveMQEventProducerPublishSubscribe(EventProducerSettingsCore eventProducerSettingsCore) {
        super(eventProducerSettingsCore);
    }

    @Override
    protected void createMessageProducer() throws JMSException {
        Topic topic = session.createTopic(eventProducerSettingsCore.getDestinationName());

        log.info("EventProducer '{}': Creating topic '{}'...", producerName, topic.getTopicName());

        producer = (ActiveMQMessageProducer) session.createProducer(topic);
    }
}
