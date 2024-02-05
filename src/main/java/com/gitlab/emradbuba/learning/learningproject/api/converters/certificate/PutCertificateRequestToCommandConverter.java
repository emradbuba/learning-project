package com.gitlab.emradbuba.learning.learningproject.api.converters.certificate;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingCertificateCommand;
import org.springframework.stereotype.Component;

@Component
public class PutCertificateRequestToCommandConverter {
    public UpdateExistingCertificateCommand toCommand(String personBusinessId, String certificateBusinessId,
                                                      PutCertificateRequest putCertificateRequest) {
        return UpdateExistingCertificateCommand.builder()
                .personBusinessId(personBusinessId)
                .certificateBusinessId(certificateBusinessId)
                .startDate(putCertificateRequest.getStartDate())
                .endDate(putCertificateRequest.getEndDate())
                .companyName(putCertificateRequest.getCompanyName())
                .build();
    }
}