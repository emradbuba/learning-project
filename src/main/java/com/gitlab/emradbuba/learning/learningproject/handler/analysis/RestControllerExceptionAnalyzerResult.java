package com.gitlab.emradbuba.learning.learningproject.handler.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RestControllerExceptionAnalyzerResult {
    private HttpStatus finalHttpStatus;
    private String rootThrowableClass;
    private String mainErrorMessage;
    private String rootErrorMessage;
    private String lpExceptionErrorCode;
    private List<String> solutionTips;
    private String personBusinessId;
}
