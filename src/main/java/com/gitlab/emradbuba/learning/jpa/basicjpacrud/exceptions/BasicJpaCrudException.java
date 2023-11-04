package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class BasicJpaCrudException extends RuntimeException {
    public BasicJpaCrudException(String message) {
        super(message);
    }

    public BasicJpaCrudException(String message, Throwable cause) {
        super(message, cause);
    }
}
