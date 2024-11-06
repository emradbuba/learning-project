package com.gitlab.emradbuba.learning.learningproject.service.commands.idcard.converter;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.idcard.AddNewIdCardCommand;
import com.gitlab.emradbuba.learning.learningproject.service.commands.NormalizationUtils;
import org.springframework.stereotype.Component;

@Component
public class PostIdCardRequestToCommandConverter {
    public AddNewIdCardCommand toCommand(String personBusinessId, PostIdCardRequest postIdCardRequest) {
        return AddNewIdCardCommand.builder()
                .personBusinessId(NormalizationUtils.normalizeString(personBusinessId))
                .serialNumber(NormalizationUtils.normalizeString(postIdCardRequest.getSerialNumber()))
                .validUntil(postIdCardRequest.getValidUntil())
                .publishedBy(NormalizationUtils.normalizeString(postIdCardRequest.getPublishedBy()))
                .build();
    }
}
