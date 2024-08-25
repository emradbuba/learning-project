package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.AbstractActiveMQEventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import jakarta.jms.JMSException;
import jakarta.jms.Topic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveMQEventConsumerPublishSubscribe extends AbstractActiveMQEventConsumer {

    public ActiveMQEventConsumerPublishSubscribe(EventConsumerSettingsCore eventConsumerSettingsCore) {
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

        final String subscriptionName = "Sub_" + topicName;

        log.info("EventConsumer '{}': Creating durable consumer | SubscriptionName: '{}'...", consumerName, subscriptionName);

        /*
          Consumer will be visible on Artemis broker depending on subscription type:
            * Durable (our case) - queue will have a name "clientId_subscriptionName"
            * Non-Durable - queue with random generated name; removed after connection is off

            So for durable subscription we will have a queue:
                <AppName::ConsumerName.Sub::TopicName>
         */

        consumer = session.createDurableConsumer(topic, subscriptionName);
    }
}
