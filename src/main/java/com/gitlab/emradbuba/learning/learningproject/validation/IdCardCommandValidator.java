package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard.PutIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LPIncorrectInputException;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class IdCardCommandValidator {

    private static final String ERROR_WHEN_VALIDATING_INPUT_DATA = "Error when validating input data";

    private IdCardCommandValidator() {
        // no instances
    }


    public static void validateAddNewIdCardCommand(AddNewIdCardCommand addNewIdCardCommand) {
        validatePublishedBy(addNewIdCardCommand.getPublishedBy());
        validateSerialNumber(addNewIdCardCommand.getSerialNumber());
        validateValidityDate(addNewIdCardCommand.getValidUntil());
    }

    public static void validateAddNewIdCardCommand(PutIdCardRequest putNewIdCardCommand) {
        validatePublishedBy(putNewIdCardCommand.getPublishedBy());
        validateSerialNumber(putNewIdCardCommand.getSerialNumber());
        validateValidityDate(putNewIdCardCommand.getValidUntil());
    }

    private static void validatePublishedBy(String publishedBy) {
        if (StringUtils.isBlank(publishedBy)) {
            throw new LPIncorrectInputException(ERROR_WHEN_VALIDATING_INPUT_DATA)
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_PUBLISHER_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_PUBLISHER_EMPTY.getDescription())
                    .withSolutionTip("Enter a serial number in the request");
        }
    }

    private static void validateSerialNumber(String idCardSerialNumber) {
        if (StringUtils.isBlank(idCardSerialNumber)) {
            throw new LPIncorrectInputException(ERROR_WHEN_VALIDATING_INPUT_DATA)
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_SERIAL_NUMBER_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_SERIAL_NUMBER_EMPTY.getDescription())
                    .withSolutionTip("Enter a serial number in the request");
        }
    }

    private static void validateValidityDate(LocalDate validUntil) {
        if (validUntil == null) {
            throw new LPIncorrectInputException(ERROR_WHEN_VALIDATING_INPUT_DATA)
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_DATE_EMPTY.name())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_DATE_EMPTY.getDescription())
                    .withSolutionTip("Enter the validity in the request");
        }
        if (!validUntil.isBefore(LocalDate.now())) {
            throw new LPIncorrectInputException(ERROR_WHEN_VALIDATING_INPUT_DATA)
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_DATE_INVALID.name())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_ID_CARD_DATE_INVALID.getDescription())
                    .withSolutionTip("Enter the valid date - make sure you try to input the correct card!");
        }
    }
}
