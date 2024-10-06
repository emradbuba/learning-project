package com.gitlab.emradbuba.learning.learningproject.service.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.LearningEventMessageFactory;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.RestApiEventMessageCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventingService {

    private final EventProducer amqPeerEventProducer;
    private final EventProducer amqPubsubEventProducer;
    private final LearningEventMessageFactory learningEventMessageFactory;

    public EventingService(@Qualifier("restAmqPeerEventsProducer") EventProducer amqPeerEventProducer,
                           @Qualifier("restAmqPubsubEventsProducer") EventProducer amqPubsubEventProducer,
                           LearningEventMessageFactory learningEventMessageFactory) {
        this.amqPeerEventProducer = amqPeerEventProducer;
        this.amqPubsubEventProducer = amqPubsubEventProducer;
        this.learningEventMessageFactory = learningEventMessageFactory;
    }

    public void sendPeerMessage(final RestApiEventMessageCommand restApiEventMessageCommand) {

        LearningAppMessage learningAppMessage = learningEventMessageFactory.fromRestCommand(restApiEventMessageCommand);

        amqPeerEventProducer.sendMessage(learningAppMessage);
    }

    public void sendPubSubMessage(final RestApiEventMessageCommand restApiEventMessageCommand) {

        LearningAppMessage learningAppMessage = learningEventMessageFactory.fromRestCommand(restApiEventMessageCommand);

        amqPubsubEventProducer.sendMessage(learningAppMessage);
    }

    public void sendVirtualTopicMessage(RestApiEventMessageCommand restApiEventMessageCommand) {

        throw new UnsupportedOperationException("VirtualTopic feature is not yet supported...");
    }
}