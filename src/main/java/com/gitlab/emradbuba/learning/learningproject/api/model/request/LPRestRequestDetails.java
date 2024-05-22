package com.gitlab.emradbuba.learning.learningproject.api.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder(toBuilder = true)
@Getter
public class LPRestRequestDetails {
    private final String endpointType;
    private final String endpointPath;
    private final String username;
    private final String userRole;
    private final LocalDateTime requestStartTime;
    @Singular
    private final Map<String, String> additionalDetails;

    private static final String UNKNOWN = "<unknown>";

    public static final LPRestRequestDetails NULL_OBJECT = LPRestRequestDetails.builder()
            .endpointPath(UNKNOWN)
            .endpointPath(UNKNOWN)
            .requestStartTime(null)
            .username(UNKNOWN)
            .userRole(UNKNOWN)
            .build();
}
