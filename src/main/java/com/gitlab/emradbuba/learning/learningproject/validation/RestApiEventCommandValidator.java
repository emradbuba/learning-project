package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing.MessageType;
import com.gitlab.emradbuba.learning.learningproject.exceptions.ExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LearningProjectIncorrectInputException;
import com.gitlab.emradbuba.learning.learningproject.service.commands.eventing.RestApiEventMessageCommand;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import static com.gitlab.emradbuba.learning.learningproject.exceptions.ExceptionErrorCode.*;

public class RestApiEventCommandValidator {

    public static void validate(RestApiEventMessageCommand restApiEventMessageCommand) {

        validateExistingUUID(restApiEventMessageCommand.getMessageId(), INCORRECT_REST_MESSAGE_ID);
        validateMessageText(restApiEventMessageCommand.getMessageContent(), INCORRECT_REST_EVENT_MESSAGE_TEXT);
        validateMessageText(restApiEventMessageCommand.getEndpointRequester(), INCORRECT_REST_EVENT_MESSAGE_REQUESTER);
        validateNotNull(restApiEventMessageCommand.getMessageType(), INCORRECT_REST_MESSAGE_TYPE);
    }

    private static void validateExistingUUID(String messageId, ExceptionErrorCode errorCode) {

        if (StringUtils.isBlank(messageId)) {
            return;
        }

        try {
            UUID.fromString(messageId);
        } catch (IllegalArgumentException e) {
            throw new LearningProjectIncorrectInputException("Error while validating RestApiEventMessageCommand")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(errorCode.getReasonCode())
                    .withDescription(errorCode.getDescription())
                    .withSolutionTip("Give an non empty message text");
        }
    }

    private static void validateMessageText(String messageText, ExceptionErrorCode errorCode) {
        if (StringUtils.isBlank(messageText)) {
            throw new LearningProjectIncorrectInputException("Error while validating RestApiEventMessageCommand")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(errorCode.getReasonCode())
                    .withDescription(errorCode.getDescription())
                    .withSolutionTip("Give an non empty message text");
        }
    }

    private static void validateNotNull(MessageType messageType, ExceptionErrorCode errorCode) {
        if (messageType == null) {
            throw new LearningProjectIncorrectInputException("Error while validating RestApiEventMessageCommand")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(errorCode.getReasonCode());
        }
    }
}
