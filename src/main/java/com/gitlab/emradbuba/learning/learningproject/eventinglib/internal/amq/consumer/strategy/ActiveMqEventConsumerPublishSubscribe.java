package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.AbstractActiveMQEventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.Topic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveMqEventConsumerPublishSubscribe extends AbstractActiveMQEventConsumer {

    public ActiveMqEventConsumerPublishSubscribe(EventConsumerSettingsCore eventConsumerSettingsCore) {
        super(eventConsumerSettingsCore);
    }

    @Override
    public void createMessageConsumer() throws JMSException {
        /*
          In case of Publish-Subscribe approach, we create a Topic which will be
          visible as an address on AMQ broker
         */

        String topicName = eventConsumerSettingsCore.getEventDestinationName();
        Topic topic = session.createTopic(topicName);

        log.info("EventConsumer '{}': Creating durable consumer of topic '{}'...", uniqueConsumerName, topic.getTopicName());
        String consumerName = String.format("%s_%s_%s", microServiceName, uniqueConsumerName, topic.getTopicName());

        consumer = session.createDurableConsumer(topic, consumerName);
    }
}
