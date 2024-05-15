package com.gitlab.emradbuba.learning.learningproject.handler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Class representing an error object returned from LearningProject API in case of errors.
 */
@Builder
@Getter
public class LPErrorResponse {
    private final String message;
    private final String httpStatusInfo;
    private final String errorCode;
    private final String solutionTips;
    private final String personBusinessId;
    private final String rootCauseClass;
    private final String rootCauseMessage;
    private final LocalDateTime restCallStartTime;
    private final LocalDateTime restCallErrorResponseTime;
}
