package com.gitlab.emradbuba.learning.jpa.basicjpacrud.exceptions;

public class CertificateNotFoundException extends BasicJpaCrudAppException {
    public CertificateNotFoundException(String message) {
        super(message);
    }

    public CertificateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
