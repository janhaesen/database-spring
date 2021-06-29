package org.databasespring.framework.database.session;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.databasespring.framework.database.connection.ReadConnection;
import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.exception.DatabaseMessage;
import org.databasespring.framework.database.session.operation.read.SelectListQueryOperation;
import org.databasespring.framework.database.session.operation.read.SelectOneQueryOperation;
import org.springframework.stereotype.Service;

@Service
public class ReadSession implements DatabaseSession {

    private final ReadConnection databaseConnection;

    public ReadSession(ReadConnection connection) {
        this.databaseConnection = connection;
    }
    /**
     * Retrieve a single row mapped from the statement key.
     *
     * @return Mapped object
     */
    public <T> T selectOne(Class<T> resultClazz, @Nonnull String statement) throws DatabaseException {
        return selectOne(resultClazz, statement, null);
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return Mapped object
     */
    public <T> T selectOne(Class<T> resultClazz, @Nonnull String statement, @CheckForNull Object parameter) throws DatabaseException {
        try {
            SelectOneQueryOperation<T> selectOneQueryOperation = new SelectOneQueryOperation<>(resultClazz, statement, parameter, databaseConnection);
            return AccessController.doPrivileged(selectOneQueryOperation);
        } catch (PrivilegedActionException e) {
            if (e.getException() instanceof DatabaseException) {
                // re-throw the exception but as DatabaseException
                throw (DatabaseException) e.getException();
            }
            throw new DatabaseException(DatabaseMessage.UNKNOWN_EXCEPTION, e);
        }
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     *
     * @param statement Unique identifier matching the statement to use.
     * @return List of mapped object
     */
    public <E> List<E> selectList(@Nonnull Class<E> resultClazz, @Nonnull String statement) throws DatabaseException {
        return selectList(resultClazz, statement, null);
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return List of mapped object
     */
    public <E> List<E> selectList(@Nonnull Class<E> resultClazz, @Nonnull String statement, @CheckForNull Object parameter) throws DatabaseException {
        return selectList(resultClazz, statement, parameter, null);
    }

    /**
     * Retrieve a list of mapped objects from the statement key and parameter, within the specified row bounds.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param rowBounds Bounds to limit object retrieval
     * @return List of mapped object
     */
    public <E> List<E> selectList(@Nonnull Class<E> resultClazz, @Nonnull String statement, @CheckForNull Object parameter, @CheckForNull RowBounds rowBounds) throws DatabaseException {
        try {
            SelectListQueryOperation<E> selectOneQueryOperation = new SelectListQueryOperation<>(resultClazz, statement, parameter, rowBounds, databaseConnection);
            return AccessController.doPrivileged(selectOneQueryOperation);
        } catch (PrivilegedActionException e) {
            if (e.getException() instanceof DatabaseException) {
                // re-throw the exception but as DatabaseException
                throw (DatabaseException) e.getException();
            }
            throw new DatabaseException(DatabaseMessage.UNKNOWN_EXCEPTION, e);
        }
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list of results into a Map based on one of the properties in the resulting objects. Eg. Return a of Map[Integer,Author] for
     * selectMap("selectAuthors","id")
     *
     * @param statement Unique identifier matching the statement to use.
     * @param mapKey The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return null;
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list of results into a Map based on one of the properties in the resulting objects.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey The property to use as key for each value in the list.
     * @return Map containing key pair data.
     */
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return null;
    }

    /**
     * The selectMap is a special case in that it is designed to convert a list of results into a Map based on one of the properties in the resulting objects.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param mapKey The property to use as key for each value in the list.
     * @param rowBounds Bounds to limit object retrieval
     * @return Map containing key pair data.
     */
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return null;
    }

    /**
     * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
     *
     * @param statement Unique identifier matching the statement to use.
     * @return Cursor of mapped objects
     */
    public <T> Cursor<T> selectCursor(String statement) {
        return null;
    }

    /**
     * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @return Cursor of mapped objects
     */
    public <T> Cursor<T> selectCursor(String statement, Object parameter) {
        return null;
    }

    /**
     * A Cursor offers the same results as a List, except it fetches data lazily using an Iterator.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param rowBounds Bounds to limit object retrieval
     * @return Cursor of mapped objects
     */
    public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
        return null;
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter using a {@code ResultHandler}.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param parameter A parameter object to pass to the statement.
     * @param handler ResultHandler that will handle each retrieved row
     */
    public void select(String statement, Object parameter, ResultHandler<?> handler) {
        // TODO: implement
    }

    /**
     * Retrieve a single row mapped from the statement using a {@code ResultHandler}.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param handler ResultHandler that will handle each retrieved row
     */
    public void select(String statement, ResultHandler<?> handler) {
        // TODO: implement
    }

    /**
     * Retrieve a single row mapped from the statement key and parameter using a {@code ResultHandler} and {@code RowBounds}.
     *
     * @param statement Unique identifier matching the statement to use.
     * @param rowBounds RowBound instance to limit the query results
     * @param handler ResultHandler that will handle each retrieved row
     */
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler<?> handler) {
        // TODO: implement
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
