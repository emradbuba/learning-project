package com.gitlab.emradbuba.learning.learningproject.persistance.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "PERSON")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "BUSINESS_ID")
    private String businessId;

    @Column(name = "FIRST_NAME", length = 100)
    @Size(min = 2, max = 100)
    @NotNull
    private String firstName;

    @Column(name = "SURNAME", length = 100)
    @Size(min = 2, max = 100)
    @NotNull
    private String surname;

    @Column(name = "DATE_OF_BIRTH")
    @NotNull
    private LocalDate dateOfBirth;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "ID_CARD_ID")
    @JsonManagedReference
    private IdCardEntity idCard;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<EmploymentCertificateEntity> certificates;
}