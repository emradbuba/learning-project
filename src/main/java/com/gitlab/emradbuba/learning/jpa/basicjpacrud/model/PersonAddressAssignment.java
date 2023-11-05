package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PersonAddressAssignment {
    @EmbeddedId
    private PersonAddressAssignmentKey id;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
