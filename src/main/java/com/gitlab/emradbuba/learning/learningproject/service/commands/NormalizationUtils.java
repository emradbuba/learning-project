package com.gitlab.emradbuba.learning.learningproject.service.commands;

import java.util.Optional;

public final class NormalizationUtils {
    private NormalizationUtils() {
        // prevent instantiation
    }

    public static String normalizeString(String stringValue) {
        return Optional.ofNullable(stringValue)
                .map(String::trim)
                .orElse("");
    }
}
