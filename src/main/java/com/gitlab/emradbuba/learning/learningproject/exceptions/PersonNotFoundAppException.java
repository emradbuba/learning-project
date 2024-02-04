package com.gitlab.emradbuba.learning.learningproject.exceptions;

public class PersonNotFoundAppException extends BasicJpaCrudAppException {
    public PersonNotFoundAppException(String message) {
        super(message);
    }
}