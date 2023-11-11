package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class BasicJpaCrudAppException extends RuntimeException {
    public BasicJpaCrudAppException(String message) {
        super(message);
    }

    public BasicJpaCrudAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
