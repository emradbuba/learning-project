package com.gitlab.emradbuba.learning.learningproject.exceptions;

public class IdCardDoesNotExistAppException extends BasicJpaCrudAppException {
    public IdCardDoesNotExistAppException(String message) {
        super(message);
    }
}