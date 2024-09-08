package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.SendEventMessageCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EventingService {

    private final EventProducer amqPeerEventProducer;
    private final EventProducer amqPubsubEventProducer;

    public EventingService(@Qualifier("amqPeerProducer") EventProducer amqPeerEventProducer,
                           @Qualifier("amqPubsubProducer") EventProducer amqPubsubEventProducer) {
        this.amqPeerEventProducer = amqPeerEventProducer;
        this.amqPubsubEventProducer = amqPubsubEventProducer;
    }

    public void sendPeerMessage(final SendEventMessageCommand sendEventMessageCommand) {
        final String messageTextToSend = String.format(
                "<PeerMsg '%s' | '%s'>", sendEventMessageCommand.getMessageUuid(), sendEventMessageCommand.getMessageText()
        );

        amqPeerEventProducer.produceMessage(messageTextToSend); // TODO: Should we return sth if message was not sent? Error? Or just log?
    }

    public void sendPubSubMessage(final SendEventMessageCommand sendEventMessageCommand) {
        final String messageTextToSend = String.format(
                "<PubSubMsg '%s' | '%s'>", sendEventMessageCommand.getMessageUuid(), sendEventMessageCommand.getMessageText()
        );

        amqPubsubEventProducer.produceMessage(messageTextToSend); // TODO: Should we return sth if message was not sent? Error? Or just log?
    }
}