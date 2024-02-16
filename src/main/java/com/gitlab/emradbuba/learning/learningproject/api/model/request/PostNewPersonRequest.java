package com.gitlab.emradbuba.learning.learningproject.api.model.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNewPersonRequest {
    @Length(min = 5)
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
}
