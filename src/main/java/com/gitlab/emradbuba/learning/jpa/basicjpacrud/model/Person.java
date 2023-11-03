package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @OneToOne(mappedBy = "person")
    private IdCard idCard;
}
