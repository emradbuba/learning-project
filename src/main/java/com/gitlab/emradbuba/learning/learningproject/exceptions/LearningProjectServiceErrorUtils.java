package com.gitlab.emradbuba.learning.learningproject.exceptions;

import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.notfound.LearningProjectNotFoundException;
import org.springframework.http.HttpStatus;

public class LearningProjectServiceErrorUtils {
    private static final String PERSON_ID_NOT_FOUND_MSG = "No person found for a given businessId: ";

    private LearningProjectServiceErrorUtils() {
        // no instance
    }

    public static LearningProjectNotFoundException createPersonNotFoundException(String personBusinessId) {
        // TODO: Refactor this ugly casting...
        return (LearningProjectNotFoundException) new LearningProjectNotFoundException(PERSON_ID_NOT_FOUND_MSG + personBusinessId)
                .withPersonBusinessId(personBusinessId)
                .withUniqueErrorCode(ExceptionErrorCode.PERSON_ID_NOT_FOUND.getReasonCode())
                .withDescription(ExceptionErrorCode.PERSON_ID_NOT_FOUND.getDescription())
                .withHttpStatusCodeValue(HttpStatus.NOT_FOUND.value());
    }
}
