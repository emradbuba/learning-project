package com.gitlab.emradbuba.learning.learningproject.exceptions;

public class BasicJpaCrudAppException extends RuntimeException {
    public BasicJpaCrudAppException(String message) {
        super(message);
    }

    public BasicJpaCrudAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
