package com.gitlab.emradbuba.learning.learningproject.api.converters.person;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingPersonCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PutExistingPersonRequestToCommandConverter {
    public UpdateExistingPersonCommand toCommand(String personBusinessId,
                                                 PutExistingPersonRequest putExistingPersonRequest) {
        return UpdateExistingPersonCommand.builder()
                .businessId(normalizeString(personBusinessId))
                .firstName(normalizeString(putExistingPersonRequest.getFirstName()))
                .surname(normalizeString(putExistingPersonRequest.getSurname()))
                .dateOfBirth(putExistingPersonRequest.getDateOfBirth())
                .build();
    }
}
