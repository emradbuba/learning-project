package com.gitlab.emradbuba.learning.learningproject.service.commands.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class LearningEventMessageFactory {

    public LearningAppMessage fromRestCommand(RestApiEventMessageCommand restApiEventMessageCommand) {

        final String newOrExistingMessageId = Optional.ofNullable(restApiEventMessageCommand.getMessageId())
                .filter(StringUtils::isNoneBlank)
                .map(String::strip)
                .orElse(UUID.randomUUID().toString());

        // TODO: How to create message so some fields are accessible by client and some for example by other class
        //  responsible for filling final fields and sending... (only)
        return LearningAppMessage.builder()
                .messageId(newOrExistingMessageId)
                .messageContent(restApiEventMessageCommand.getMessageContent())
                .messageType(restApiEventMessageCommand.getMessageType().getStandardMessageTypeName())
                .messageTrigger(restApiEventMessageCommand.getEndpoint())
                .messageSender(restApiEventMessageCommand.getEndpointRequester())
                .messageSenderApp("LearningProject(API)")
                .createdDateTime(LocalDateTime.now())
                .build();
    }
}
