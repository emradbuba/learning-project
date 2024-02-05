package com.gitlab.emradbuba.learning.learningproject.service.converters;

import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import com.gitlab.emradbuba.learning.learningproject.persistance.model.EmploymentCertificateEntity;
import org.springframework.stereotype.Component;

@Component
public class CertificateEntityToCertificateConverter {
    public EmploymentCertificate fromCertificateEntity(EmploymentCertificateEntity entity) {
        return EmploymentCertificate.builder()
                .businessId(entity.getBusinessId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .companyName(entity.getCompanyName())
                .build();
    }
}
