package com.gitlab.emradbuba.learning.learningproject.api.converters.certificate;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewCertificateCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostCertificateRequestToCommandConverter {
    public AddNewCertificateCommand toCommand(String personBusinessId, PostCertificateRequest postCertificateRequest) {
        return AddNewCertificateCommand.builder()
                .personBusinessId(normalizeString(personBusinessId))
                .startDate(postCertificateRequest.getStartDate())
                .endDate(postCertificateRequest.getEndDate())
                .companyName(normalizeString(postCertificateRequest.getCompanyName()))
                .build();
    }
}