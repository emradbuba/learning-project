package com.gitlab.emradbuba.learning.learningproject.service.commands.person.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PostNewPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.person.AddNewPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PostNewPersonRequestToCommandConverter {
    public AddNewPersonCommand toCommand(PostNewPersonRequest postNewPersonRequest) {
        return AddNewPersonCommand.builder()
                .firstName(NormalizationUtils.normalizeString(postNewPersonRequest.getFirstName()))
                .surname(NormalizationUtils.normalizeString(postNewPersonRequest.getSurname()))
                .dateOfBirth(postNewPersonRequest.getDateOfBirth())
                .build();
    }
}
