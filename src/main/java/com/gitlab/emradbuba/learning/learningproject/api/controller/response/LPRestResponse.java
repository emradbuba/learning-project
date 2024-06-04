package com.gitlab.emradbuba.learning.learningproject.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LPRestResponse<T> {
    private LPRestResponseDetailedInfo detailedInfo;
    private T payload;
}
