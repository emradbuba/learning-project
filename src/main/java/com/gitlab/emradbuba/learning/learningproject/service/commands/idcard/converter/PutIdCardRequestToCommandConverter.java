package com.gitlab.emradbuba.learning.learningproject.service.commands.idcard.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard.PutIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.idcard.UpdateExistingIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PutIdCardRequestToCommandConverter {
    public UpdateExistingIdCardCommand toCommand(String personBusinessId, PutIdCardRequest putIdCardRequest) {
        return UpdateExistingIdCardCommand.builder()
                .personBusinessId(NormalizationUtils.normalizeString(personBusinessId))
                .publishedBy(NormalizationUtils.normalizeString(putIdCardRequest.getPublishedBy()))
                .serialNumber(NormalizationUtils.normalizeString(putIdCardRequest.getSerialNumber()))
                .validUntil(putIdCardRequest.getValidUntil())
                .build();
    }
}
