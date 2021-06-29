package org.databasespring.framework.database.session.operation;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.databasespring.framework.database.connection.DatabaseConnection;
import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.exception.DatabaseMessage;

public abstract class AbstractQueryOperation<R extends AbstractQueryOperation<R, T>, T> implements PrivilegedExceptionAction<T>, QueryOperation<T> {

    private final DatabaseConnection databaseConnection;

    protected AbstractQueryOperation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Performs the computation.  This method will be called by {@code AccessController.doPrivileged} after enabling privileges.
     *
     * @return a class-dependent value that may represent the results of the computation.  Each class that implements {@code PrivilegedExceptionAction} should document what (if anything) this value
     * represents.
     * @throws Exception an exceptional condition has occurred.  Each class that implements {@code PrivilegedExceptionAction} should document the exceptions that its run method can throw.
     * @see AccessController#doPrivileged(PrivilegedExceptionAction)
     * @see AccessController#doPrivileged(PrivilegedExceptionAction, AccessControlContext)
     */
    @Override
    public T run() throws Exception {
        try (SqlSession sqlSession = databaseConnection.getSqlSessionFactory().openSession(databaseConnection.getExecutorType(), databaseConnection.isAutoCommit())) {
            return this.execute(sqlSession);
        } catch (PersistenceException | DatabaseException e) {
            throw new DatabaseException(DatabaseMessage.UNABLE_TO_EXECUTE, e);
        }
    }
}
