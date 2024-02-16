package com.gitlab.emradbuba.learning.learningproject.demo.validation.custom.driving;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DrivingLicenseCategoryValidator.class)
public @interface DrivingLicenseCategory {
    String message() default "Driving license category should be one character: 'A', 'B' or 'C'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
