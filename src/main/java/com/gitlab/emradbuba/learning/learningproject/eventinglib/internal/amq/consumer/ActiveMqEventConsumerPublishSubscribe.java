package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Topic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveMqEventConsumerPublishSubscribe extends AbstractActiveMQEventConsumer {
    public ActiveMqEventConsumerPublishSubscribe(EventConsumerSettingsCore eventConsumerSettingsCore) {
        super(eventConsumerSettingsCore);
    }

    @Override
    protected MessageConsumer createMessageConsumer() throws JMSException {

        String topicName = eventConsumerSettingsCore.getSourceName();
        Topic topic = session.createTopic(topicName);
        log.info("EventConsumer '{}': Creating durable consumer to topic '{}'...", uniqueConsumerName, topicName);

        return session.createDurableConsumer(topic, uniqueConsumerName);
    }
}
