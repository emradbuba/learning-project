package com.gitlab.emradbuba.learning.learningproject.api.converters;

import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NormalizationUtilsTest {

    @Test
    void normalizeStringShouldReturnExpectedStringWhenWasAlreadyCorrect() {
        final String correctString = "correct-string";

        String result = NormalizationUtils.normalizeString(correctString);

        assertThat(result)
                .isNotBlank()
                .isEqualTo(correctString);
    }

    @Test
    void normalizeStringShouldReturnExpectedStringWhenWasNeedsNormalization() {
        final String correctString = "correct-string";

        String result = NormalizationUtils.normalizeString("   " + correctString + "     ");

        assertThat(result)
                .isNotBlank()
                .isEqualTo(correctString);
    }

    @Test
    void normalizeStringShouldReturnEmptyStringWhenNullInput() {
        String result = NormalizationUtils.normalizeString(null);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void normalizeStringShouldReturnEmptyStringWhenEmptyInput() {
        String result = NormalizationUtils.normalizeString("");

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void normalizeStringShouldReturnEmptyStringWhenEmptyInputAfterNormalization() {
        String result = NormalizationUtils.normalizeString("    ");

        assertThat(result)
                .isNotNull()
                .isEqualTo("");
    }
}