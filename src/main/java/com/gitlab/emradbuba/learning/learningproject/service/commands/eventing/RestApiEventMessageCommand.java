package com.gitlab.emradbuba.learning.learningproject.service.commands.eventing;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class RestApiEventMessageCommand { // TODO: Can command be records?

    private final String messageId;
    private final String messageContent;
    private final String endpointRequester;
    private final String endpoint;
    private final MessageType messageType;
}
