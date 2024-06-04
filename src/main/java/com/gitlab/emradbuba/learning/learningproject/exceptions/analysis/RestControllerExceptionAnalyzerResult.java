package com.gitlab.emradbuba.learning.learningproject.exceptions.analysis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RestControllerExceptionAnalyzerResult {
    private HttpStatus lpExwceptionFinalHttpStatus;
    private String rootThrowableClass;
    private String mainErrorMessage;
    private String rootErrorMessage;
    private String lpExceptionErrorCode;
    private String lpExceptionDescription;
    private List<String> lpExceptionSolutionTips;
    private String personBusinessId;
}
