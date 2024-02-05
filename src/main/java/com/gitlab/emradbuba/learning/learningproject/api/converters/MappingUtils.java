package com.gitlab.emradbuba.learning.learningproject.api.converters;

import java.util.Optional;

public final class MappingUtils {
    private MappingUtils() {
        // prevent instantiation
    }

    public static String normalizeString(String stringValue) {
        return Optional.ofNullable(stringValue)
                .map(String::trim)
                .orElse("");
    }
}
