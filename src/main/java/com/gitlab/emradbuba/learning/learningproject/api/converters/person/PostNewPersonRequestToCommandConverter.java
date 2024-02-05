package com.gitlab.emradbuba.learning.learningproject.api.converters.person;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewPersonCommand;
import org.springframework.stereotype.Component;

@Component
public class PostNewPersonRequestToCommandConverter {
    public AddNewPersonCommand toCommand(PostNewPersonRequest postNewPersonRequest) {
        return AddNewPersonCommand.builder()
                .firstName(postNewPersonRequest.getFirstName())
                .surname(postNewPersonRequest.getSurname())
                .dateOfBirth(postNewPersonRequest.getDateOfBirth())
                .build();
    }
}
