package com.gitlab.emradbuba.learning.learningproject.api.model.request.person;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PutExistingPersonRequest {

    @Schema(description = "New first name of a person",
            type = "string",
            example = "Michael",
            requiredMode = REQUIRED)
    private String firstName;

    @Schema(description = "New surname of a person",
            type = "string",
            example = "Greenway",
            requiredMode = REQUIRED)
    private String surname;

    @Schema(type = "string",
            description = "New date of birth",
            format = "date",
            example = "2020-02-17",
            requiredMode = REQUIRED)
    private LocalDate dateOfBirth;
}