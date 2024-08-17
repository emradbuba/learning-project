package com.gitlab.emradbuba.learning.learningproject.eventinglib.internal.lifecycle;

public interface EventingLifecycleEntity {

    /**
     * Starts the eventing lifecycle entity in a way suitable for specific implementation
     */
    void startEventingLifecycleEntity();

    /**
     * Stops the eventing lifecycle entity in a way suitable for specific implementation
     */
    void stopEventingLifecycleEntity();
}
