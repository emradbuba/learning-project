package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LearningAppAmqMessage {

    private final String messageContent;
    private final String messageType;
    private final String messageTrigger;
    private final String messageSendingUser;
    private final String messageSendingApplication;
    private final LocalDateTime createdDateTime;

    @Setter
    private LocalDateTime sentDateTime;
    @Setter
    private String messageId;
}
