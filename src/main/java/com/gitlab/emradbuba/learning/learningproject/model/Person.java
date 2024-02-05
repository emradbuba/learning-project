package com.gitlab.emradbuba.learning.learningproject.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
public class Person {
    private String businessId;
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
    private IdCard idCard;
    private Set<EmploymentCertificate> certificates;
}
