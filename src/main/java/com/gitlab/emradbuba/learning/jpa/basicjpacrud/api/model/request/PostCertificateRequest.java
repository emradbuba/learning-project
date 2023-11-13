package com.gitlab.emradbuba.learning.jpa.basicjpacrud.api.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCertificateRequest {
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
}
