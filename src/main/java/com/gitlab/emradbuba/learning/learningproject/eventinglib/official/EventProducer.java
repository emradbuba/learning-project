package com.gitlab.emradbuba.learning.learningproject.eventinglib.official;

import com.gitlab.emradbuba.learning.learningproject.eventinglib.official.model.LearningAppMessage;

public interface EventProducer {

    void sendMessage(LearningAppMessage message);


    // These are methods to support fake multi/app approach - would not be available in normal conditions
    String getName();
    String getApplicationName();
}
