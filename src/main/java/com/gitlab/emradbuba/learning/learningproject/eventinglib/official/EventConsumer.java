package com.gitlab.emradbuba.learning.learningproject.eventinglib.official;

public interface EventConsumer {

    // These are methods to support fake multi/app approach - would not be available in normal conditions
    String getName();
    String getApplicationName();
}
