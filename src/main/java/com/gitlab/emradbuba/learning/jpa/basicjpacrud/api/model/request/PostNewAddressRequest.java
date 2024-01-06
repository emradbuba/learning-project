package com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNewAddressRequest {
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String zipCode;
    private String city;
    private String country;
    private boolean isPrivate;
    private boolean isOffice;
    private boolean isCorrespondence;
}
