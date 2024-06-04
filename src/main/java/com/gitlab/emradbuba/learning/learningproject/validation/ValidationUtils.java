package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LPIncorrectInputException;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public final class ValidationUtils {
    private ValidationUtils() {
        // util class - no instances
    }

    public static void validateUUID(final String uuidNumber) {
        try {
            UUID.fromString(uuidNumber);
        } catch (IllegalArgumentException iae) {
            throw new LPIncorrectInputException("Incorrect UUID format: '" + uuidNumber + "'")
                    .withHttpStatusCodeValue(422)
                    .withSolutionTip("Correct the number so it follows the UUID standard")
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_UUID_FORMAT.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_UUID_FORMAT.getDescription());

        }

    }
}
