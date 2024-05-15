package com.gitlab.emradbuba.learning.learningproject.handler;

import com.gitlab.emradbuba.learning.learningproject.handler.analysis.RestControllerExceptionAnalyzerResult;
import com.gitlab.emradbuba.learning.learningproject.handler.analysis.RestControllerExceptionAnalyzer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class RestControllerExceptionHandler {

    private static final String ERROR_SOLUTION_TIP_DELIMITER = " :: ";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<LPErrorResponse> handleException(Exception exception) {

        final RestControllerExceptionAnalyzer exceptionAnalyzer = new RestControllerExceptionAnalyzer(exception);
        final RestControllerExceptionAnalyzerResult analyzerResult = exceptionAnalyzer.analyzeThrowable();

        final HttpStatus httpStatus = analyzerResult.getFinalHttpStatus();

        return new ResponseEntity<>(
                LPErrorResponse.builder()
                        .message(analyzerResult.getMainErrorMessage())
                        .httpStatusInfo(String.format("[%d] %s", httpStatus.value(), httpStatus.getReasonPhrase()))
                        .rootCauseClass(analyzerResult.getRootThrowableClass())
                        .rootCauseMessage(analyzerResult.getRootErrorMessage())
                        .errorCode(analyzerResult.getLpExceptionErrorCode())
                        .solutionTips(String.join(ERROR_SOLUTION_TIP_DELIMITER, analyzerResult.getSolutionTips()))
                        .personBusinessId(analyzerResult.getPersonBusinessId())
                        .restCallErrorResponseTime(LocalDateTime.now())
                        .build(),
                httpStatus
        );
    }
}
