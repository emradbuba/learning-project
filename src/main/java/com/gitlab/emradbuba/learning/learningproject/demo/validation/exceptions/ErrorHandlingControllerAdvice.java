package com.gitlab.emradbuba.learning.learningproject.demo.validation.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Profile("validation")
class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String onConstraintValidationException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            sb.append("Validation failed for field '")
                    .append(violation.getPropertyPath().toString())
                    .append("' | Reason: ")
                    .append(violation.getMessage()).append("\n");
        }
        return sb.toString();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError violation : e.getBindingResult().getFieldErrors()) {
            sb.append("Validation failed for field '")
                    .append(violation.getField())
                    .append("' | Reason: ")
                    .append(violation.getDefaultMessage()).append("\n");
        }
        return sb.toString();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String onRuntimeException(RuntimeException e) {
        StringBuilder sb = new StringBuilder("Error while processing the request: '")
                .append(e.getMessage())
                .append("'\n");
        return sb.toString();
    }

}
