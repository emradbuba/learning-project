package com.gitlab.emradbuba.learning.learningproject.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class EmploymentCertificate {
    private final String businessId;
    private final String companyName;
    private final LocalDate startDate;
    private final LocalDate endDate;
}
