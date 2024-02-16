package com.gitlab.emradbuba.learning.learningproject.demo.validation.dto.request;

import com.gitlab.emradbuba.learning.learningproject.demo.validation.custom.driving.DrivingLicenseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DrivingLicenseDto {

    @NotBlank
    @DrivingLicenseCategory
    public String category;

    @NotNull
    @Past // date has to be from the past
    public LocalDate creationDate;

    @NotNull
    public LocalDate validityDate;
}
