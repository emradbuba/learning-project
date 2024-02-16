package com.gitlab.emradbuba.learning.learningproject.api.converters.idcard;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PutIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.UpdateExistingIdCardCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PutIdCardRequestToCommandConverter {
    public UpdateExistingIdCardCommand toCommand(String personBusinessId, PutIdCardRequest putIdCardRequest) {
        return UpdateExistingIdCardCommand.builder()
                .personBusinessId(normalizeString(personBusinessId))
                .publishedBy(normalizeString(putIdCardRequest.getPublishedBy()))
                .serialNumber(normalizeString(putIdCardRequest.getSerialNumber()))
                .validUntil(putIdCardRequest.getValidUntil())
                .build();
    }
}
