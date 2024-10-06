package com.gitlab.emradbuba.learning.learningproject.service.commands.certificate.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.certificate.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.certificate.AddNewCertificateCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PostCertificateRequestToCommandConverter {
    public AddNewCertificateCommand toCommand(String personBusinessId, PostCertificateRequest postCertificateRequest) {
        return AddNewCertificateCommand.builder()
                .personBusinessId(NormalizationUtils.normalizeString(personBusinessId))
                .startDate(postCertificateRequest.getStartDate())
                .endDate(postCertificateRequest.getEndDate())
                .companyName(NormalizationUtils.normalizeString(postCertificateRequest.getCompanyName()))
                .build();
    }
}