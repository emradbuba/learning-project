package com.gitlab.emradbuba.learning.learningproject.handler.analysis;

import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.LPException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RestControllerExceptionAnalyzer {

    private static final HttpStatus DEFAULT_ERROR_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR; // 500

    private final LinkedList<Throwable> throwableChainStartingFromRoot;
    private final Throwable mainThrowable;
    private LPException firstLearningProjectException;

    public RestControllerExceptionAnalyzer(Throwable mainThrowable) {
        this.mainThrowable = mainThrowable;
        this.throwableChainStartingFromRoot = new LinkedList<>(List.of(mainThrowable));
    }

    public RestControllerExceptionAnalyzerResult analyzeThrowable() {
        initThrowableChain();
        initFirstLearningProjectException();

        return RestControllerExceptionAnalyzerResult.builder()
                .finalHttpStatus(determineFinalHttpStatus())
                .mainErrorMessage(determineMainErrorMessage())
                .rootErrorMessage(determineRootThrowableErrorMessage())
                .lpExceptionErrorCode(determineLPExceptionErrorCode())
                .rootThrowableClass(throwableChainStartingFromRoot.getFirst().getClass().getSimpleName())
                .solutionTips(determineSolutionTips())
                .personBusinessId(determinePersonBusinessId())
                .build();
    }

    private void initThrowableChain() {
        Throwable currentThrowable = mainThrowable;
        while (currentThrowable.getCause() != null) {
            throwableChainStartingFromRoot.add(currentThrowable.getCause());
            currentThrowable = currentThrowable.getCause();
        }
        Collections.reverse(throwableChainStartingFromRoot);
    }

    private void initFirstLearningProjectException() {
        this.firstLearningProjectException = this.throwableChainStartingFromRoot.stream()
                .filter(LPException.class::isInstance)
                .map(LPException.class::cast)
                .findFirst()
                .orElse(null);
    }

    private HttpStatus determineFinalHttpStatus() {
        return Optional.ofNullable(firstLearningProjectException)
                .map(LPException::getHttpStatusCodeValue)
                .map(HttpStatus::resolve)
                .orElse(DEFAULT_ERROR_HTTP_STATUS);
    }

    private String determineMainErrorMessage() {
        return mainThrowable.getMessage();
    }

    private String determineRootThrowableErrorMessage() {
        return Optional.ofNullable(throwableChainStartingFromRoot.getFirst())
                .map(Throwable::getMessage)
                .filter(StringUtils::isNoneBlank)
                .orElse(null);
    }

    private String determineLPExceptionErrorCode() {
        return Optional.ofNullable(firstLearningProjectException)
                .map(LPException::getMessage)
                .filter(StringUtils::isNoneBlank)
                .orElse(null);
    }

    private List<String> determineSolutionTips() {
        return throwableChainStartingFromRoot.stream()
                .filter(LPException.class::isInstance)
                .map(LPException.class::cast)
                .map(LPException::getSolutionTip)
                .filter(StringUtils::isNoneBlank)
                .toList();
    }

    private String determinePersonBusinessId() {
        return throwableChainStartingFromRoot.stream()
                .filter(LPException.class::isInstance)
                .map(LPException.class::cast)
                .map(LPException::getPersonBusinessId)
                .filter(StringUtils::isNoneBlank)
                .findFirst()
                .orElse(null);
    }
}
