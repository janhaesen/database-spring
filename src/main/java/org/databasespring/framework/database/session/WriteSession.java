package org.databasespring.framework.database.session;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.databasespring.framework.database.connection.WriteConnection;
import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.exception.DatabaseMessage;
import org.databasespring.framework.database.session.operation.write.DeleteQueryOperation;
import org.databasespring.framework.database.session.operation.write.InsertQueryOperation;
import org.databasespring.framework.database.session.operation.write.UpdateQueryOperation;
import org.springframework.stereotype.Service;

@Service
public class WriteSession implements DatabaseSession {

    private final WriteConnection databaseConnection;

    public WriteSession(WriteConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Execute an insert statement.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the insert.
     */
    public int insert(@Nonnull String statement) throws DatabaseException {
        return insert(statement, null);
    }

    /**
     * Execute an insert statement with the given parameter object. Any generated autoincrement values or selectKey entries will modify the given parameter object properties. Only the number of rows
     * affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the insert.
     */
    public int insert(@Nonnull String statement, @CheckForNull Object parameter) throws DatabaseException {
        if (! databaseConnection.isAutoCommit()) {
            throw new DatabaseException("Unable to execute, not autocommit");
        }

        try {
            InsertQueryOperation insertQueryOperation = new InsertQueryOperation(statement, parameter, databaseConnection);
            return AccessController.doPrivileged(insertQueryOperation);
        } catch (PrivilegedActionException e) {
            if (e.getException() instanceof DatabaseException) {
                // re-throw the exception but as DatabaseException
                throw (DatabaseException) e.getException();
            }
            throw new DatabaseException(DatabaseMessage.UNKNOWN_EXCEPTION, e);
        }
    }

    /**
     * Execute an update statement. The number of rows affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the update.
     */
    public int update(@Nonnull String statement) throws DatabaseException {
        return update(statement, null);
    }

    /**
     * Execute an update statement. The number of rows affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the update.
     */
    public int update(@Nonnull String statement, @CheckForNull Object parameter) throws DatabaseException {
        if (! databaseConnection.isAutoCommit()) {
            throw new DatabaseException("Unable to execute update, not autocommit");
        }

        try {
            UpdateQueryOperation updateQueryOperation = new UpdateQueryOperation(statement, parameter, databaseConnection);
            return AccessController.doPrivileged(updateQueryOperation);
        } catch (PrivilegedActionException e) {
            if (e.getException() instanceof DatabaseException) {
                // re-throw the exception but as DatabaseException
                throw (DatabaseException) e.getException();
            }
            throw new DatabaseException(DatabaseMessage.UNKNOWN_EXCEPTION, e);
        }
    }

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @return int The number of rows affected by the delete.
     */
    public int delete(@Nonnull String statement) throws DatabaseException {
        return delete(statement, null);
    }

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     *
     * @param statement Unique identifier matching the statement to execute.
     * @param parameter A parameter object to pass to the statement.
     * @return int The number of rows affected by the delete.
     */
    public int delete(@Nonnull String statement, @CheckForNull Object parameter) throws DatabaseException {
        if (! databaseConnection.isAutoCommit()) {
            throw new DatabaseException("Unable to execute, not autocommit");
        }

        try {
            DeleteQueryOperation updateQueryOperation = new DeleteQueryOperation(statement, parameter, databaseConnection);
            return AccessController.doPrivileged(updateQueryOperation);
        } catch (PrivilegedActionException e) {
            if (e.getException() instanceof DatabaseException) {
                // re-throw the exception but as DatabaseException
                throw (DatabaseException) e.getException();
            }
            throw new DatabaseException(DatabaseMessage.UNKNOWN_EXCEPTION, e);
        }
    }

    /**
     * Execute a custom query, for instance to apply multiple queries in the same session
     *
     * @param customSessionManager that is to be executed, ususally lambda function
     * @param <T> class type that should be returned
     * @return the result class by the type that is provided
     * @throws DatabaseException when unable to execute the query
     */
    public <T> T custom(@Nonnull CustomSessionManager<T> customSessionManager) throws DatabaseException {
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<T>) () -> {
                try (SqlSession sqlSession = databaseConnection.getSqlSessionFactory().openSession(databaseConnection.getExecutorType(), databaseConnection.isAutoCommit())) {
                    return customSessionManager.apply(sqlSession);
                } catch (PersistenceException e) {
                    throw new DatabaseException(DatabaseMessage.UNABLE_TO_EXECUTE, e);
                }
            });
        } catch (PrivilegedActionException e) {
            if (e.getException() instanceof DatabaseException) {
                throw (DatabaseException) e.getException();
            }
            throw new DatabaseException("Unknown exception type", e);
        }
    }

}
