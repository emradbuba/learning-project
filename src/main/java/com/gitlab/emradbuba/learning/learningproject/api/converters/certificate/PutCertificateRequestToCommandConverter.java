package com.gitlab.emradbuba.learning.learningproject.api.converters.certificate;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.certificate.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingCertificateCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PutCertificateRequestToCommandConverter {
    public UpdateExistingCertificateCommand toCommand(String personBusinessId, String certificateBusinessId,
                                                      PutCertificateRequest putCertificateRequest) {
        return UpdateExistingCertificateCommand.builder()
                .personBusinessId(normalizeString(personBusinessId))
                .certificateBusinessId(normalizeString(certificateBusinessId))
                .startDate(putCertificateRequest.getStartDate())
                .endDate(putCertificateRequest.getEndDate())
                .companyName(normalizeString(putCertificateRequest.getCompanyName()))
                .build();
    }
}