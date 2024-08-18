package com.gitlab.emradbuba.learning.learningproject.service;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.EventProducer;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.SendEventMessageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventingService {

    //private final EventProducer eventProducer;

    public void sendMessage(final SendEventMessageCommand sendEventMessageCommand) {
        final String messageTextToSend = String.format(
                "EventMessage [%s]: '%s'", sendEventMessageCommand.getMessageUuid(), sendEventMessageCommand.getMessageText()
        );

        //eventProducer.produceMessage(messageTextToSend); // TODO: Should we return sth if message was not sent? Error? Or just log?
    }
}
