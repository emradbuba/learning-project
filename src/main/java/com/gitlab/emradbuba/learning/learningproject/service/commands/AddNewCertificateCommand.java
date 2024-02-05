package com.gitlab.emradbuba.learning.learningproject.service.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class AddNewCertificateCommand {
    private final String personBusinessId;
    private final String companyName;
    private final LocalDate startDate;
    private final LocalDate endDate;
}
