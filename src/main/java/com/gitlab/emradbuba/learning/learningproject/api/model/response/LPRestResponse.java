package com.gitlab.emradbuba.learning.learningproject.api.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Getter
public class LPRestResponse<T> {

    private final String httpStatusCodeInfo;
    private final String calledEndpointMethod;
    private final String calledEndpointPath;
    private final Long requestProcessingDuration;
    private final LocalDateTime requestProcessingStartTime;
    private final T payload;
    @Singular("addExtraInformation")
    private final Map<String, String> extraInformationMap;
}
