package com.gitlab.emradbuba.learning.learningproject.api.controller;

import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.converter.PostEventMessageRequestToCommandConverter;
import com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing.PostEventMessageRequest;
import com.gitlab.emradbuba.learning.learningproject.service.EventingService;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.SendEventMessageCommand;
import com.gitlab.emradbuba.learning.learningproject.validation.EventMessageCommandValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This is just a controller to test eventing. Basic purpose it to trigger message sending and control event handling via API calls
 */
@RestController
@RequestMapping("/api/v1/eventing")
@AllArgsConstructor
@Tag(name = "Eventing methods", description = "This part of API serves testing methods for eventing")
public class EventingController {

    private final EventingService eventingService;
    private final PostEventMessageRequestToCommandConverter postEventMessageRequestToCommandConverter;

    @PostMapping("/peer/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sends a message to a configured message broker (PeerToPeer approach", description = "Translates the request to a message and send via producer to event broker")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "When message accepted to be send"),
            @ApiResponse(responseCode = "422", description = "When request cannot be processed due to incorrect request")}
    )
    public void sendPeerMessage(@RequestBody PostEventMessageRequest postEventMessageRequest) {

        SendEventMessageCommand sendEventMessageCommand = postEventMessageRequestToCommandConverter.toCommand(postEventMessageRequest);
        EventMessageCommandValidator.validateSendEventMessageCommand(sendEventMessageCommand);

        eventingService.sendPeerMessage(sendEventMessageCommand);
    }

    @PostMapping("/pubsub/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Sends a message to a configured message broker (PubSub approach)", description = "Translates the request to a message and send via producer to event broker")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "When message accepted to be send"),
            @ApiResponse(responseCode = "422", description = "When request cannot be processed due to incorrect request")}
    )
    public void sendPubSubMessage(@RequestBody PostEventMessageRequest postEventMessageRequest) {

        SendEventMessageCommand sendEventMessageCommand = postEventMessageRequestToCommandConverter.toCommand(postEventMessageRequest);
        EventMessageCommandValidator.validateSendEventMessageCommand(sendEventMessageCommand);

        eventingService.sendPubSubMessage(sendEventMessageCommand);
    }
}
