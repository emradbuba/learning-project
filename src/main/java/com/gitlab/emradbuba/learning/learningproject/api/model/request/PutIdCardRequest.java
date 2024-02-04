package com.gitlab.emradbuba.learning.learningproject.api.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PutIdCardRequest {
    private String serialNumber;
    private LocalDate validUntil;
    private String publishedBy;
}
