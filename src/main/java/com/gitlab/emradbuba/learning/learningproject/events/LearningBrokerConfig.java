package com.gitlab.emradbuba.learning.learningproject.events;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class LearningBrokerConfig {

    @Value("${eventing.amq.broker.url}")
    private String brokerUrl;

    @Value("${eventing.amq.broker.username}")
    private String username;

    @Value("${eventing.amq.broker.password}")
    private String password;
}
