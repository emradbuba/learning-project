package com.gitlab.emradbuba.learning.learningproject.exceptions;

import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.LPException;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.notfound.LPPersonNotFoundException;
import org.springframework.http.HttpStatus;

public class LPServiceExceptionUtils {
    private static final String PERSON_ID_NOT_FOUND_MSG = "No person found for a given businessId: ";

    private LPServiceExceptionUtils() {
        // no instance
    }

    public static LPPersonNotFoundException createLPPersonNotFoundException(String personBusinessId) {
        // TODO: Refactor this ugly casting...
        return (LPPersonNotFoundException) new LPPersonNotFoundException(PERSON_ID_NOT_FOUND_MSG + personBusinessId)
                .withPersonBusinessId(personBusinessId)
                .withUniqueErrorCode(LPServiceExceptionErrorCode.PERSON_ID_NOT_FOUND.getReasonCode())
                .withDescription(LPServiceExceptionErrorCode.PERSON_ID_NOT_FOUND.getDescription())
                .withHttpStatusCodeValue(HttpStatus.NOT_FOUND.value());
    }
}
