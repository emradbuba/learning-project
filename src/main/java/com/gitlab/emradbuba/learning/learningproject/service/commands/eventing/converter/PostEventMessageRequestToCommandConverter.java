package com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing.PostEventMessageRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.SendEventMessageCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PostEventMessageRequestToCommandConverter {

    public SendEventMessageCommand toCommand(PostEventMessageRequest postEventMessageRequest) {
        return SendEventMessageCommand.builder()
                .messageUuid(NormalizationUtils.normalizeString(postEventMessageRequest.getMessageId()))
                .messageText(NormalizationUtils.normalizeString(postEventMessageRequest.getMessageToSend()))
                .build();
    }
}
