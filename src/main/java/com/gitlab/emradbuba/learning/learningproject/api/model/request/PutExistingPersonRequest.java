package com.gitlab.emradbuba.learning.learningproject.api.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PutExistingPersonRequest {
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
}