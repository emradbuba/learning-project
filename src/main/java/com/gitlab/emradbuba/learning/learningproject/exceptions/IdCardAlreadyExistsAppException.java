package com.gitlab.emradbuba.learning.learningproject.exceptions;

public class IdCardAlreadyExistsAppException extends BasicJpaCrudAppException {
    public IdCardAlreadyExistsAppException(String message) {
        super(message);
    }
}