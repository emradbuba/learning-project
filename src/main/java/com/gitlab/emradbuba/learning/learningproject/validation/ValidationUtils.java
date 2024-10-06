package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.exceptions.ExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LearningProjectIncorrectInputException;

import java.util.UUID;

public final class ValidationUtils {
    private ValidationUtils() {
        // util class - no instances
    }

    public static void validateUUID(final String uuidNumber) {
        try {
            UUID.fromString(uuidNumber);
        } catch (IllegalArgumentException iae) {
            throw new LearningProjectIncorrectInputException("Incorrect UUID format: '" + uuidNumber + "'")
                    .withHttpStatusCodeValue(422)
                    .withSolutionTip("Correct the number so it follows the UUID standard")
                    .withUniqueErrorCode(ExceptionErrorCode.INCORRECT_UUID_FORMAT.getReasonCode())
                    .withDescription(ExceptionErrorCode.INCORRECT_UUID_FORMAT.getDescription());

        }
    }
}
