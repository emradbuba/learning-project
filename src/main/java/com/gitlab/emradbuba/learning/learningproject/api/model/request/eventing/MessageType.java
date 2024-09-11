package com.gitlab.emradbuba.learning.learningproject.api.model.request.eventing;

public enum MessageType {
    CREATE_MESSAGE("CREATE"),
    UPDATE_MESSAGE("UPDATED");

    private final String standardMessageTypeName;

    MessageType(String standardMessageTypeName) {
        this.standardMessageTypeName = standardMessageTypeName;
    }

    public String getStandardMessageTypeName() {
        return standardMessageTypeName;
    }
}
