package com.gitlab.emradbuba.learning.learningproject.eventinglib.official.settings;

// TODO: This has to be discussed in terms of different brokers
public enum EventCommunicationModelType {
    AMQ_PEER_TO_PEER,
    AMQ_PUBLISH_SUBSCRIBE,
    AMQ_VIRTUAL_TOPIC;
}
