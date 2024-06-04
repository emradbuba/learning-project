package com.gitlab.emradbuba.learning.learningproject.api.controller.response;

import com.gitlab.emradbuba.learning.learningproject.api.controller.request.LPRestRequestContext;
import com.gitlab.emradbuba.learning.learningproject.api.controller.response.LPRestResponseDetailedInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LPRestResponseDetailsCreator {

    @Value("${app.version:unknown}")
    private String version;

    public LPRestResponseDetailedInfo create(LPRestRequestContext restRequestContext, HttpStatus httpStatus) {
        return new LPRestResponseDetailedInfo()
                .withRequester(String.format("%s (%s)", restRequestContext.getUsername(), restRequestContext.getUserRole()))
                .withLearningProjectVersionInfo(version)
                .withEndpoint(String.format("[%s] %s", restRequestContext.getEndpointType(), restRequestContext.getEndpointPath()))
                .withResult(String.format("[%d] %s", httpStatus.value(), httpStatus.getReasonPhrase()));
    }
}
