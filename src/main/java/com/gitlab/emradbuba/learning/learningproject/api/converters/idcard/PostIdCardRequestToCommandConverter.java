package com.gitlab.emradbuba.learning.learningproject.api.converters.idcard;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.idcard.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import org.springframework.stereotype.Component;

import static com.gitlab.emradbuba.learning.learningproject.api.converters.MappingUtils.normalizeString;

@Component
public class PostIdCardRequestToCommandConverter {
    public AddNewIdCardCommand toCommand(String personBusinessId, PostIdCardRequest postIdCardRequest) {
        return AddNewIdCardCommand.builder()
                .personBusinessId(normalizeString(personBusinessId))
                .serialNumber(normalizeString(postIdCardRequest.getSerialNumber()))
                .validUntil(postIdCardRequest.getValidUntil())
                .publishedBy(normalizeString(postIdCardRequest.getPublishedBy()))
                .build();
    }
}
