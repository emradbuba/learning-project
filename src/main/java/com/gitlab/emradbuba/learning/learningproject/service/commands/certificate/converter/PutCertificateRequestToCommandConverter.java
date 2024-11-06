package com.gitlab.emradbuba.learning.learningproject.service.commands.certificate.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.certificate.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.certificate.UpdateExistingCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PutCertificateRequestToCommandConverter {
    public UpdateExistingCertificateCommand toCommand(String personBusinessId, String certificateBusinessId,
                                                      PutCertificateRequest putCertificateRequest) {
        return UpdateExistingCertificateCommand.builder()
                .personBusinessId(NormalizationUtils.normalizeString(personBusinessId))
                .certificateBusinessId(NormalizationUtils.normalizeString(certificateBusinessId))
                .startDate(putCertificateRequest.getStartDate())
                .endDate(putCertificateRequest.getEndDate())
                .companyName(NormalizationUtils.normalizeString(putCertificateRequest.getCompanyName()))
                .build();
    }
}