package com.gitlab.emradbuba.learning.learningproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "EMPLOYMENT_CERTIFICATE")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmploymentCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COMPANY_NAME")
    @NotNull
    private String companyName;

    @Column(name = "START_DATE")
    @NotNull
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @NotNull
    private LocalDate endDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "person")
    private Person person;
}