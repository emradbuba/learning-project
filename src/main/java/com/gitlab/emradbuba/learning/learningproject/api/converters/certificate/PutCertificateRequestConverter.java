package com.gitlab.emradbuba.learning.learningproject.api.converters.certificate;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutCertificateRequest;
import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PutCertificateRequestConverter {
    public EmploymentCertificate toBusinessModel(PutCertificateRequest putCertificateRequest) {
        EmploymentCertificate employmentCertificate = new EmploymentCertificate();
        employmentCertificate.setCompanyName(normalizeString(putCertificateRequest.getCompanyName()));
        employmentCertificate.setStartDate(putCertificateRequest.getStartDate());
        employmentCertificate.setEndDate(putCertificateRequest.getEndDate());
        return employmentCertificate;
    }
}