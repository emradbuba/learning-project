package com.gitlab.emradbuba.learning.learningproject.eventinglib.official;

public interface EventProducer {

    // TODO: 1. This message should take a 'Message' parameter to sent something, even an Object - message should be an object taking body and metadata - see the structure of AMQ messages and model it...
    void produceMessage(String text);
}
