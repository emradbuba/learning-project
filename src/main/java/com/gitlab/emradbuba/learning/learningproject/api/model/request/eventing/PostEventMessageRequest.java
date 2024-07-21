package com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEventMessageRequest {

    @Schema(description = "MessageId in a form of UUID", type = "uuid", example = "", requiredMode = REQUIRED)
    private String messageId;

    @Schema(description = "Content of a message (text) to be sent via event", type = "string", example = "Sample text message", requiredMode = REQUIRED)
    private String messageToSend;
}
