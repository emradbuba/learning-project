package com.gitlab.emradbuba.learning.learningproject;

import java.util.UUID;

public class BusinessIdUtils {
    public static final String UNKNOWN_BUSINESS_ID = "UNKNOWN_BUSINESS_ID";

    public static String generateBusinessId() {
        return UUID.randomUUID().toString();
    }
}
