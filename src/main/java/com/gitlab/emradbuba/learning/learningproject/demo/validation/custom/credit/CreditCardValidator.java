package com.gitlab.emradbuba.learning.learningproject.demo.validation.custom.credit;

import com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request.CreditCardDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardValidator implements ConstraintValidator<ValidCreditCard, CreditCardDto> {
    private static final List<String> ALLOWED_CREDIT_CARD_TYPES = Arrays.asList("VISA", "MASTERCARD", "MAESTRO");
    private static final String ALLOWED_CREDIT_CARD_TYPES_STRING = String.join(", ", ALLOWED_CREDIT_CARD_TYPES);
    private static final Pattern CREDIT_CARD_NUMBER_PATTERN = Pattern.compile("([0-9]{4}-){3}[0-9]{4}");
    private static final BigInteger MIN_LIMIT = BigInteger.valueOf(1000L);

    @Override
    public boolean isValid(CreditCardDto creditCardDto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        final String creditCardType = creditCardDto.getCreditCardType();
        if (!ALLOWED_CREDIT_CARD_TYPES.contains(creditCardType)) {
            String message = String.format("Credit card type %s not accepted. Accepted type: %s", creditCardType,
                    ALLOWED_CREDIT_CARD_TYPES_STRING);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        Matcher matcher = CREDIT_CARD_NUMBER_PATTERN.matcher(creditCardDto.getCreditCardNumber());
        if (!matcher.matches()) {
            context.buildConstraintViolationWithTemplate(
                            "Credit card number not accepted. Accepted format: 0000-0000-0000-0000")
                    .addConstraintViolation();
            return false;
        }
        if (creditCardDto.getCreditLimit().compareTo(MIN_LIMIT) < 0) {
            String message = String.format("Credit card limit not accepted. Minimum limit: %s", MIN_LIMIT);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
