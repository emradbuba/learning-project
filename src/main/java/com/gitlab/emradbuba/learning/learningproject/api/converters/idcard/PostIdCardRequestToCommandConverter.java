package com.gitlab.emradbuba.learning.learningproject.api.converters.idcard;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.PostIdCardRequest;
import com.gitlab.emradbuba.learning.learningproject.service.commands.AddNewIdCardCommand;
import org.springframework.stereotype.Component;

@Component
public class PostIdCardRequestToCommandConverter {
    public AddNewIdCardCommand toCommand(String personBusinessId, PostIdCardRequest postIdCardRequest) {
        return AddNewIdCardCommand.builder()
                .personBusinessId(personBusinessId)
                .serialNumber(postIdCardRequest.getSerialNumber())
                .validUntil(postIdCardRequest.getValidUntil())
                .publishedBy(postIdCardRequest.getPublishedBy())
                .build();
    }
}
