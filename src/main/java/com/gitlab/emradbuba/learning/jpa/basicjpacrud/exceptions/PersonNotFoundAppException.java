package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class PersonNotFoundAppException extends BasicJpaCrudAppException {
    public PersonNotFoundAppException(String message) {
        super(message);
    }
}