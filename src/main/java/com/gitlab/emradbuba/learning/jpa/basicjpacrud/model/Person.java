package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERSON")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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

    /* OneToOne options:
        - ForeignKey approach (used here)
        - Shared Primary Key
        - JoinTable
    */
    @OneToOne(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private IdCard idCard;

    @OneToMany(mappedBy = "person")
    private Set<EmploymentCertificate> certificates;

    @OneToMany(mappedBy = "person")
    private Set<PersonAddressAssignment> personAddressAssignments;
}
