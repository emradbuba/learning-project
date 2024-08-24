package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.factory;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy.ActiveMQEventConsumerPeerToPeer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy.ActiveMQEventConsumerPublishSubscribe;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.consumer.strategy.ActiveMQEventConsumerVirtualTopic;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventConsumerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventConsumer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import org.springframework.stereotype.Component;

@Component
public class AmqEventConsumerFactory {

    public EventConsumer createEventConsumer(final EventConsumerSettingsCore eventConsumerSettingsCore) {

        EventCommunicationModelType eventCommunicationModelType = eventConsumerSettingsCore.getEventCommunicationModelType();

        return switch (eventCommunicationModelType) {
            case AMQ_PEER_TO_PEER -> new ActiveMQEventConsumerPeerToPeer(eventConsumerSettingsCore);
            case AMQ_PUBLISH_SUBSCRIBE -> new ActiveMQEventConsumerPublishSubscribe(eventConsumerSettingsCore);
            case AMQ_VIRTUAL_TOPIC -> new ActiveMQEventConsumerVirtualTopic(eventConsumerSettingsCore);
        };
    }
}