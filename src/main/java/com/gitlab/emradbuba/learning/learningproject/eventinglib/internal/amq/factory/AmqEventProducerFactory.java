package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.factory;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.strategy.ActiveMQEventProducerPeerToPeer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.amq.producer.strategy.ActiveMQEventProducerPublishSubscribe;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.settings.EventProducerSettingsCore;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings.EventCommunicationModelType;
import org.springframework.stereotype.Component;

@Component
public class AmqEventProducerFactory {

    public EventProducer createEventProducer(final EventProducerSettingsCore eventProducerSettingsCore) {

        EventCommunicationModelType eventCommunicationModelType = eventProducerSettingsCore.getEventCommunicationModelType();

        return switch (eventCommunicationModelType) {
            case AMQ_PEER_TO_PEER -> new ActiveMQEventProducerPeerToPeer(eventProducerSettingsCore);
            case AMQ_PUBLISH_SUBSCRIBE -> new ActiveMQEventProducerPublishSubscribe(eventProducerSettingsCore);
            case AMQ_VIRTUAL_TOPIC -> throw new UnsupportedOperationException("VirtualTopics feature is not yet implemented");
        };
    }
}
