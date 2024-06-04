package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LPIncorrectInputException;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class PersonCommandValidator {
    private static final int MIN_AGE = 18;

    private PersonCommandValidator() {
        // no instance
    }

    public static void validateAddNewPersonCommand(final AddNewPersonCommand addNewPersonCommand) {
        validateFirstName(addNewPersonCommand.getFirstName());
        validateSurname(addNewPersonCommand.getSurname());
        validateDateOfBirth(addNewPersonCommand.getDateOfBirth());
    }

    public static void validateUpdateExistingPersonCommand(final UpdateExistingPersonCommand updateExistingPersonCommand) {
        validateFirstName(updateExistingPersonCommand.getFirstName());
        validateSurname(updateExistingPersonCommand.getSurname());
        validateDateOfBirth(updateExistingPersonCommand.getDateOfBirth());
    }

    private static void validateFirstName(final String firstName) {
        if (StringUtils.isBlank(firstName)) {
            throw new LPIncorrectInputException("Incorrect person input data")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_PERSON_FIRST_NAME_IS_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_PERSON_FIRST_NAME_IS_EMPTY.getDescription())
                    .withSolutionTip("Provide a non-empty person first name");
        }
    }

    private static void validateSurname(String surname) {
        if (StringUtils.isBlank(surname)) {
            throw new LPIncorrectInputException("Incorrect person input data")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_PERSON_SURNAME_IS_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_PERSON_SURNAME_IS_EMPTY.getDescription())
                    .withSolutionTip("Provide a non-empty person surname");
        }
    }

    private static void validateDateOfBirth(final LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new LPIncorrectInputException("Incorrect person input data")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_PERSON_BIRTH_DATE_IS_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_PERSON_BIRTH_DATE_IS_EMPTY.getDescription())
                    .withSolutionTip("Provide a non-empty person date");
        }
        boolean isUnder18 = dateOfBirth.isAfter(LocalDate.now().minusYears(MIN_AGE));
        if (isUnder18) {
            throw new LPIncorrectInputException("Incorrect person input data")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_PERSON_BIRTH_DATE_IS_TOO_YOUNG.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_PERSON_BIRTH_DATE_IS_TOO_YOUNG.getDescription())
                    .withSolutionTip("Person must be over 18 - check the date or try when he/she is 18");
        }
    }
}
