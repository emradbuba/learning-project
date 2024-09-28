package com.gitlab.emradbuba.learning.learningproject.eventinglib.official;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;

public interface EventProducer {

    void sendMessage(LearningAppMessage message);
}
