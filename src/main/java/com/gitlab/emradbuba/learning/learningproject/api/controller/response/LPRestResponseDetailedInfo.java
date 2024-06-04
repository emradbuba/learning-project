package com.gitlab.emradbuba.learning.learningproject.api.controller.response;

import lombok.Getter;

import java.util.Map;

@Getter
public class LPRestResponseDetailedInfo {
    private String learningProjectVersionInfo;
    private String endpoint;
    private String result;
    private String requester;
    private Map<String, String> additionalInfo;

    public LPRestResponseDetailedInfo withLearningProjectVersionInfo(final String version) {
        this.learningProjectVersionInfo = version;
        return this;
    }

    public LPRestResponseDetailedInfo withRequester(final String requester) {
        this.requester = requester;
        return this;
    }

    public LPRestResponseDetailedInfo withEndpoint(final String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public LPRestResponseDetailedInfo withResult(final String result) {
        this.result = result;
        return this;
    }
}
