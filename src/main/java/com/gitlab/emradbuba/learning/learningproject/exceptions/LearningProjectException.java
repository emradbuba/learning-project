package com.gitlab.emradbuba.learning.learningproject.exceptions;

public class LearningProjectException extends RuntimeException {
    public LearningProjectException(String message) {
        super(message);
    }

    public LearningProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
