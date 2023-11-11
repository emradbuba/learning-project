package com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostIdCardRequest {
    private String serialNumber;
    private LocalDate validUntil;
    private String publishedBy;
}
