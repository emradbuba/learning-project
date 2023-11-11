package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class IdCardDoesNotExistAppException extends BasicJpaCrudAppException {
    public IdCardDoesNotExistAppException(String message) {
        super(message);
    }
}