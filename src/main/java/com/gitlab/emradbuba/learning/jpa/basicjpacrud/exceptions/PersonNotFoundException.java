package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class PersonNotFoundException extends BasicJpaCrudException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
