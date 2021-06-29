package org.databasespring.framework.database.connection;

import org.databasespring.framework.database.session.ReadSession;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
public class ReadConnection implements DatabaseConnection {

    private final SqlSessionFactory sqlSessionFactory;
    private static final boolean READ_ONLY = true;
    private static final boolean AUTO_COMMIT = true;
    private static final ExecutorType EXECUTOR_TYPE = ExecutorType.SIMPLE;

    public ReadConnection(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public boolean isReadOnly() {
        return READ_ONLY;
    }

    @Override
    public boolean isAutoCommit() {
        return AUTO_COMMIT;
    }

    @Override
    public ExecutorType getExecutorType() {
        return EXECUTOR_TYPE;
    }

    @Override
    public ReadSession session() {
        return new ReadSession(this);
    }

}
