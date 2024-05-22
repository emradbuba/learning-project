package com.gitlab.emradbuba.learning.learningproject.api.model.response;

import com.gitlab.emradbuba.learning.learningproject.api.model.request.LPRestRequestDetails;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LPRestResponseDetails {
    private final String learningProjectVersion;
    private final LPRestRequestDetails requestContext;
}
