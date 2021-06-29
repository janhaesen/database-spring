package org.databasespring.framework.database.exception;

public enum DatabaseMessage {
    UNABLE_TO_EXECUTE("Unable to execute query"),
    UNKNOWN_EXCEPTION("Unknown exception type"),
    ;

    private final String message;

    DatabaseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
