package com.gitlab.emradbuba.learning.learningproject.service.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Builder
@Getter
public class AddNewPersonCommand {
    private final String firstName;
    private final String surname;
    private final LocalDate dateOfBirth;
}
