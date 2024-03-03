package com.gitlab.emradbuba.learning.learningproject.api.converters.person;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostNewPersonRequestToCommandConverter {
    public AddNewPersonCommand toCommand(PostNewPersonRequest postNewPersonRequest) {
        return AddNewPersonCommand.builder()
                .firstName(normalizeString(postNewPersonRequest.getFirstName()))
                .surname(normalizeString(postNewPersonRequest.getSurname()))
                .dateOfBirth(postNewPersonRequest.getDateOfBirth())
                .build();
    }
}
