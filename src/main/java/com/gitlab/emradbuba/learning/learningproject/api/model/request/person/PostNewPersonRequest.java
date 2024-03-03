package com.gitlab.emradbuba.learning.learningproject.api.model.request.person;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNewPersonRequest {

    @Schema(description = "First name of a person",
            type = "string",
            example = "Michael",
            requiredMode = REQUIRED)
    private String firstName;

    @Schema(description = "Surname of a person",
            type = "string",
            example = "Greenway",
            requiredMode = REQUIRED)
    private String surname;

    @Schema(type = "string",
            description = "Date of birth",
            format = "date",
            example = "2020-02-17",
            requiredMode = REQUIRED)
    private LocalDate dateOfBirth;
}
