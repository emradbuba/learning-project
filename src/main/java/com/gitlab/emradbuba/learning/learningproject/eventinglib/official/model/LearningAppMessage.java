package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LearningAppMessage {

    private String messageId;
    private final String messageContent;
    private final String messageType;
    private final String messageTrigger;
    private final String messageSender;
    private final String messageSenderApp;
    private final LocalDateTime createdDateTime;
}
