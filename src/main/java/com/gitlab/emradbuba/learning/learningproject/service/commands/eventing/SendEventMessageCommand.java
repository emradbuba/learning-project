package com.gitlab.emradbuba.learning.learningproject.service.commands.eventing;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class SendEventMessageCommand { // TODO: Can command be records?

    private final String messageUuid;
    private final String messageText;
}
