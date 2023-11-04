package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "EMPLOYMENT_CERTIFICATE")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmploymentCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Person person;
}