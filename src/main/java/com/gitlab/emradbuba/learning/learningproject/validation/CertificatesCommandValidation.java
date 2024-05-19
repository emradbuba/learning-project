package com.gitlab.emradbuba.learning.learningproject.validation;

import com.gitlab.emradbuba.learning.learningproject.exceptions.LPServiceExceptionErrorCode;
import com.gitlab.emradbuba.learning.learningproject.libs.exceptions.core.unprocessable.LPIncorrectInputException;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingCertificateCommand;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class CertificatesCommandValidation {

    public static void validateAddNewCertificateCommand(AddNewCertificateCommand addNewCertificateCommand) {
        validatePersonId(addNewCertificateCommand.getPersonBusinessId());
        validateDates(addNewCertificateCommand.getStartDate(), addNewCertificateCommand.getEndDate());
        validateCompanyName(addNewCertificateCommand.getCompanyName());
    }

    private static void validateCompanyName(String companyName) {
        if (StringUtils.isBlank(companyName)) {
            throw new LPIncorrectInputException("Company name must be specified")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_CERT_COMPANY_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_CERT_COMPANY_EMPTY.getDescription());
        }
    }

    public static void validateUpdateExitingCertificateCommand(UpdateExistingCertificateCommand updateExistingCertificateCommand) {
        validatePersonId(updateExistingCertificateCommand.getPersonBusinessId());
        validateCertificateId(updateExistingCertificateCommand.getCertificateBusinessId());
        validateDates(updateExistingCertificateCommand.getStartDate(), updateExistingCertificateCommand.getEndDate());
        validateCompanyName(updateExistingCertificateCommand.getCompanyName());
    }

    private static void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new LPIncorrectInputException("Start and end date must be specified")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_CERT_DATES_NULL.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_CERT_DATES_NULL.getDescription())
                    .withSolutionTip("Fix dates");
        }
        if (!endDate.isAfter(startDate)) {
            throw new LPIncorrectInputException("Start and end date must be specified")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_CERT_DATES_RELATION.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_CERT_DATES_RELATION.getDescription())
                    .withSolutionTip("Maybe you put dates in wrong order?");
        }
    }

    private static void validatePersonId(String personBusinessId) {
        if (StringUtils.isBlank(personBusinessId)) {
            throw new LPIncorrectInputException("Person business id must be specified")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_CERT_CERT_ID_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_CERT_CERT_ID_EMPTY.getDescription());
        }
    }

    private static void validateCertificateId(String certificateBusinessId) {
        if (StringUtils.isBlank(certificateBusinessId)) {
            throw new LPIncorrectInputException("Certificate business id must be specified")
                    .withHttpStatusCodeValue(422)
                    .withUniqueErrorCode(LPServiceExceptionErrorCode.INCORRECT_CERT_PERSON_ID_EMPTY.getReasonCode())
                    .withDescription(LPServiceExceptionErrorCode.INCORRECT_CERT_PERSON_ID_EMPTY.getDescription());
        }
    }

}
