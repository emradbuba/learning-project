package com.gitlab.emradbuba.learning.learningproject.service.commands.eventing;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppAmqMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class LearningEventMessageFactory {

    public LearningAppAmqMessage fromRestCommand(RestApiEventMessageCommand restApiEventMessageCommand) {

        final String messageId = Optional.ofNullable(restApiEventMessageCommand.getMessageId())
                .filter(StringUtils::isNoneBlank)
                .orElse(UUID.randomUUID().toString());

        // TODO: How to create message so some fields are accessible by client and some for example by other class
        //  responsible for filling final fields and sending... (only)
        return LearningAppAmqMessage.builder()
                .messageId(messageId)
                .messageContent(restApiEventMessageCommand.getMessageContent())
                .messageType(restApiEventMessageCommand.getMessageType().getStandardMessageTypeName())
                .messageTrigger(restApiEventMessageCommand.getEndpoint())
                .messageSendingUser(restApiEventMessageCommand.getEndpointRequester())
                .messageSendingApplication("LearningProject(API)")
                .createdDateTime(LocalDateTime.now())
                .sentDateTime(LocalDateTime.now()) // this date is irrelevant - even if defined should be overridden by producer before sending...
                .build();
    }
}
