package com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostIdCardRequest {

    @Schema(description = "Document's serial number",
            type = "string",
            example = "ABC123456",
            requiredMode = REQUIRED)
    private String serialNumber;

    @Schema(type = "string",
            description = "Date until id card is valid",
            format = "date",
            example = "2020-02-17",
            requiredMode = REQUIRED)
    private LocalDate validUntil;

    @Schema(description = "Information about who published the IdCard",
            type = "string",
            example = "President of Warsaw City",
            requiredMode = REQUIRED)
    private String publishedBy;
}
