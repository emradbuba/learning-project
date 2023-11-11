package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class IdCardAlreadyExistsAppException extends BasicJpaCrudAppException {
    public IdCardAlreadyExistsAppException(String message) {
        super(message);
    }
}