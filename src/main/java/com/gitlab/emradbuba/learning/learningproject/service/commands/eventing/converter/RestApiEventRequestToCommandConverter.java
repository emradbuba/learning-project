package com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing.PostEventMessageRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.RestApiEventMessageCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class RestApiEventRequestToCommandConverter {

    public RestApiEventMessageCommand convert(PostEventMessageRequest postEventMessageRequest) {

        final String requester = getUsernameFromRequest();
        final String endpoint = getCalledEndpointFromRequest();

        return RestApiEventMessageCommand.builder()
                .messageId(postEventMessageRequest.getMessageId())
                .messageContent(NormalizationUtils.normalizeString(postEventMessageRequest.getMessageContent()))
                .messageType(postEventMessageRequest.getMessageType())
                .endpoint(endpoint)
                .endpointRequester(requester)
                .build();
    }

    private String getUsernameFromRequest() {
        // TODO: Retrieve user from security context
        return "sample_username";
    }

    private String getCalledEndpointFromRequest() {
        // TODO: Retrieve endpoint
        return "[POST] /api/v1/the/sample/endpoint";
    }
}
