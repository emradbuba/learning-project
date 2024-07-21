package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LPIncorrectInputException;
import com.gitlab.emradbuba.learning.learningproject.service.commands.SendEventMessageCommand;
import org.apache.commons.lang3.StringUtils;

import static com.gitlab.emradbuba.learning.learningproject.validation.ValidationUtils.validateUUID;

public class EventMessageCommandValidator {

    public static void validateSendEventMessageCommand(SendEventMessageCommand sendEventMessageCommand) {
        validateUUID(sendEventMessageCommand.getMessageUuid());
        validateMessageText(sendEventMessageCommand.getMessageText());
    }

    private static void validateMessageText(String messageText) {
        if (StringUtils.isBlank(messageText)) {
            throw new LPIncorrectInputException("Error when validating event message text")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_EVENT_MESSAGE_TEXT.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_EVENT_MESSAGE_TEXT.getDescription())
                    .withSolutionTip("Give an non empty message text");
        }
    }
}
