package com.gitlab.emradbuba.learning.learningproject.api.model.request.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCertificateRequest {

    @Schema(description = "Name of the company the person was employed in", type = "string", example = "Fuergo Inc.", requiredMode = REQUIRED)
    private String companyName;

    @Schema(type = "string",
            description = "Start date of the contract",
            format = "date",
            example = "2000-02-17",
            requiredMode = REQUIRED)
    private LocalDate startDate;

    @Schema(type = "string",
            description = "Finish date of the contract",
            format = "date",
            example = "2021-02-17",
            requiredMode = REQUIRED)
    private LocalDate endDate;
}
