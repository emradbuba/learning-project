package com.gitlab.emradbuba.learning.learningproject.api.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.Map;

@Builder(toBuilder = true)
@Getter
public class LPRestRequestContext {
    private String endpointType;
    private String endpointPath;
    private String username;
    private String userRole;
    private LocalDateTime requestStartTime;
    @Singular
    private final Map<String, String> additionalDetails;

    private static final String UNKNOWN = "<unknown>";

    public static final LPRestRequestContext NULL_OBJECT = LPRestRequestContext.builder()
            .endpointPath(UNKNOWN)
            .endpointPath(UNKNOWN)
            .requestStartTime(null)
            .username(UNKNOWN)
            .userRole(UNKNOWN)
            .build();
}
