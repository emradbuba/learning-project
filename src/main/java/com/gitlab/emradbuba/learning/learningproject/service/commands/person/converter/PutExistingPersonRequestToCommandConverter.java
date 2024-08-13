package com.gitlab.emradbuba.learning.learningproject.service.commands.person.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.person.PutExistingPersonRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.person.UpdateExistingPersonCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PutExistingPersonRequestToCommandConverter {
    public UpdateExistingPersonCommand toCommand(String personBusinessId, PutExistingPersonRequest putExistingPersonRequest) {
        return UpdateExistingPersonCommand.builder()
                .businessId(NormalizationUtils.normalizeString(personBusinessId))
                .firstName(NormalizationUtils.normalizeString(putExistingPersonRequest.getFirstName()))
                .surname(NormalizationUtils.normalizeString(putExistingPersonRequest.getSurname()))
                .dateOfBirth(putExistingPersonRequest.getDateOfBirth())
                .build();
    }
}
