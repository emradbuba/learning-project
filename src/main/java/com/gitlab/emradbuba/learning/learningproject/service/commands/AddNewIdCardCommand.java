package com.gitlab.emradbuba.learning.learningproject.service.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class AddNewIdCardCommand {
    private final String personBusinessId;
    private final String serialNumber;
    private final LocalDate validUntil;
    private final String publishedBy;
}
