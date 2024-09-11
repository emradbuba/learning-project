package com.gitlab.emradbuba.learning.learningproject.eventinglib.official;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppAmqMessage;

public interface EventProducer {

    void sendMessage(LearningAppAmqMessage message);
}
