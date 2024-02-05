package com.gitlab.emradbuba.learning.learningproject.persistance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String street;
    @NotNull
    private String houseNumber;
    private String apartmentNumber;
    @NotNull
    private String zipCode;
    @NotNull
    private String city;
    @NotNull
    private String country;

//    @ManyToMany(mappedBy = "addresses")
//    private Set<Person> persons;
}
