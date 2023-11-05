package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;


/*
* Note that a composite key class has to fulfill some key requirements:
* - We have to mark it with @Embeddable.
* - It has to implement java.io.Serializable.
* - We need to provide an implementation of the hashcode() and equals() methods.
*/

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class PersonAddressAssignmentKey /*implements Serializable*/ {
    @Column(name = "PERSON_ID")
    private Long personId;

    @Column(name = "ADDRESS_ID")
    private Long addressId;
}
