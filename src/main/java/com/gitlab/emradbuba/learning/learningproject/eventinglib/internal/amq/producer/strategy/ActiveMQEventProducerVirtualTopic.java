package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.AbstractActiveMQEventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import jakarta.jms.JMSException;

public class ActiveMQEventProducerVirtualTopic extends AbstractActiveMQEventProducer {

    public ActiveMQEventProducerVirtualTopic(EventProducerSettingsCore eventProducerSettingsCore) {
        super(eventProducerSettingsCore);
    }

    @Override
    protected void createMessageProducer() throws JMSException {
        throw new UnsupportedOperationException("This operation is not yet implemented");
    }
}
