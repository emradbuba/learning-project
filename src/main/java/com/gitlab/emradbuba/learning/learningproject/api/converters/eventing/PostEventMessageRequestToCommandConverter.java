package com.gitlab.emradbuba.learning.learningproject.api.converters.eventing;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing.PostEventMessageRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.SendEventMessageCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostEventMessageRequestToCommandConverter {

    public SendEventMessageCommand toCommand(PostEventMessageRequest postEventMessageRequest) {
        return SendEventMessageCommand.builder()
                .messageUuid(normalizeString(postEventMessageRequest.getMessageId()))
                .messageText(normalizeString(postEventMessageRequest.getMessageToSend()))
                .build();
    }
}
