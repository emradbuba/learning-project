package com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEventMessageRequest {

    @Schema(description = "Specifies the type of event which can be relevant in terms of handling logic", requiredMode = REQUIRED)
    private MessageType messageType;

    @Schema(description = "Content of a message (text) to be sent via event", type = "string", example = "Sample text message", requiredMode = REQUIRED)
    private String messageContent;

    @Schema(description = "Optional message UUID - if not specified it will be generated", type = "string", example = "f2f05535-6372-4cf7-825e-7c0a2a43b6bf", requiredMode = NOT_REQUIRED)
    private String messageId;
}
