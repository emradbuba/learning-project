package com.gitlab.emradbuba.learning.learningproject.api.model.response;

import lombok.*;

@Builder
@Getter
public class LPRestResponse<T> {
    private final LPRestResponseDetails details;
    private final T payload;
}
