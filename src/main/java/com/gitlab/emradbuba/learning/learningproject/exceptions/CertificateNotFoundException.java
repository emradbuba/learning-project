package com.gitlab.emradbuba.learning.learningproject.exceptions;

public class CertificateNotFoundException extends BasicJpaCrudAppException {
    public CertificateNotFoundException(String message) {
        super(message);
    }

    public CertificateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
