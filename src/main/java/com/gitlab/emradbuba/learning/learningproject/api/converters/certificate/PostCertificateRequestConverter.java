package com.gitlab.emradbuba.learning.learningproject.api.converters.certificate;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostCertificateRequestConverter {
    public EmploymentCertificate toBusinessModel(PostCertificateRequest postCertificateRequest) {
        EmploymentCertificate employmentCertificate = new EmploymentCertificate();
        employmentCertificate.setCompanyName(normalizeString(postCertificateRequest.getCompanyName()));
        employmentCertificate.setStartDate(postCertificateRequest.getStartDate());
        employmentCertificate.setEndDate(postCertificateRequest.getEndDate());
        return employmentCertificate;
    }
}