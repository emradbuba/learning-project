package com.gitlab.emradbuba.learning.learningproject.api.converters;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MappingUtilsTest {

    @Test
    void normalizeStringShouldReturnExpectedStringWhenWasAlreadyCorrect() {
        final String correctString = "correct-string";

        String result = MappingUtils.normalizeString(correctString);

        assertThat(result)
                .isNotBlank()
                .isEqualTo(correctString);
    }

    @Test
    void normalizeStringShouldReturnExpectedStringWhenWasNeedsNormalization() {
        final String correctString = "correct-string";

        String result = MappingUtils.normalizeString("   " + correctString + "     ");

        assertThat(result)
                .isNotBlank()
                .isEqualTo(correctString);
    }

    @Test
    void normalizeStringShouldReturnEmptyStringWhenNullInput() {
        String result = MappingUtils.normalizeString(null);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void normalizeStringShouldReturnEmptyStringWhenEmptyInput() {
        String result = MappingUtils.normalizeString("");

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void normalizeStringShouldReturnEmptyStringWhenEmptyInputAfterNormalization() {
        String result = MappingUtils.normalizeString("    ");

        assertThat(result)
                .isNotNull()
                .isEqualTo("");
    }
}