package com.gitlab.emradbuba.learning.learningproject.api.controller.details;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.LPRestRequestDetails;
import com.gitlab.emradbuba.learning.learningproject.api.model.response.LPRestResponseDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LPRestResponseDetailsCreator {
    @Value("${app.version:unknown}")
    private String version;

    public LPRestResponseDetails createResponseDetails(LPRestRequestDetails restRequestDetails) {
        return LPRestResponseDetails.builder()
                .requestContext(restRequestDetails)
                .learningProjectVersion(version)
                .build();
    }
}
