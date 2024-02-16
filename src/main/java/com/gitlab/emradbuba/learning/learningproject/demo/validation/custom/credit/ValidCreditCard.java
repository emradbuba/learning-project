package com.gitlab.emradbuba.learning.learningproject.demo.validation.custom.credit;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreditCardValidator.class)
public @interface ValidCreditCard {
    String message() default "Invalid credit card...";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
