package com.gitlab.emradbuba.learning.learningproject.exceptions;

import com.gitlab.emradbuba.learning.learningproject.exceptions.analysis.RestControllerExceptionAnalyzerResult;
import com.gitlab.emradbuba.learning.learningproject.exceptions.analysis.RestControllerExceptionAnalyzer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@AllArgsConstructor
public class RestControllerExceptionHandler {

    private static final String ERROR_SOLUTION_TIP_DELIMITER = " :: ";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<LPErrorResponse> handleException(Exception exception) {

        final RestControllerExceptionAnalyzer exceptionAnalyzer = new RestControllerExceptionAnalyzer(exception);
        final RestControllerExceptionAnalyzerResult analyzerResult = exceptionAnalyzer.analyzeThrowable();

        final HttpStatus httpStatus = analyzerResult.getLpExwceptionFinalHttpStatus();

        return new ResponseEntity<>(
                LPErrorResponse.builder()
                        .message(analyzerResult.getMainErrorMessage())
                        .httpStatusInfo(String.format("[%d] %s", httpStatus.value(), httpStatus.getReasonPhrase()))
                        .rootCauseClass(analyzerResult.getRootThrowableClass())
                        .rootCauseMessage(analyzerResult.getRootErrorMessage())
                        .lpErrorCode(analyzerResult.getLpExceptionErrorCode())
                        .lpErrorDescription(analyzerResult.getLpExceptionDescription())
                        .lpSolutionTips(String.join(ERROR_SOLUTION_TIP_DELIMITER, analyzerResult.getLpExceptionSolutionTips()))
                        .personBusinessId(analyzerResult.getPersonBusinessId())
                        .restCallErrorResponseTime(LocalDateTime.now())
                        .build(),
                httpStatus
        );
    }
}
