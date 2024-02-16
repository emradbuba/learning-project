package com.gitlab.emradbuba.learning.learningproject.demo.validation.custom.driving;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DrivingLicenseCategoryValidator implements ConstraintValidator<DrivingLicenseCategory, String> {
    private static final String[] allowedCategories = new String[]{"A", "B", "C"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(allowedCategories).contains(value);
    }
}
