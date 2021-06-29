package org.databasespring.framework.database.exception;

public class DatabaseException extends Exception {

    private final String message;
    private final String statement;
    private final Throwable cause;

    /**
     * Constructs a new exception with the specified detail message.  The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public DatabaseException(String message) {
        super(message);
        this.message = message;
        this.cause = null;
        this.statement = null;
    }

    /**
     * Constructs a new exception with the specified detail message.  The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public DatabaseException(String message, String statement) {
        super(message);
        this.message = message;
        this.statement = statement;
        this.cause = null;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.  <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this exception's
     * detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.statement = null;
        this.cause = cause;
    }

    public DatabaseException(DatabaseMessage databaseMessage, Throwable cause) {
        super(databaseMessage.getMessage(), cause);
        this.message = databaseMessage.getMessage();
        this.statement = null;
        this.cause = cause;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.  <p>Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this exception's
     * detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @since 1.4
     */
    public DatabaseException(String message, String statement, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.statement = statement;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getStatement() {
        return statement;
    }

    @Override
    public synchronized Throwable getCause() {
        return cause;
    }
}
