package org.databasespring.framework.database.connection;

import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.session.DatabaseSession;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

/**
 * The type of database connection we want to have
 */
@Service
public interface DatabaseConnection {

    SqlSessionFactory getSqlSessionFactory();

    boolean isReadOnly();

    boolean isAutoCommit();

    ExecutorType getExecutorType();

    DatabaseSession session() throws DatabaseException;

}
