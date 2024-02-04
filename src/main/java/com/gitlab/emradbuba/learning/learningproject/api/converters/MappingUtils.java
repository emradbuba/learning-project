package com.gitlab.emradbuba.learning.learningproject.api.converters;

import java.util.Optional;

public class MappingUtils {
    public static String normalizeString(String stringValue) {
        return Optional.ofNullable(stringValue)
                .map(String::trim)
                .orElse("");
    }
}
