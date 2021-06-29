package org.databasespring.framework.database.connection;

import org.databasespring.framework.database.session.WriteSession;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
public class WriteConnection implements DatabaseConnection {

    private final SqlSessionFactory sqlSessionFactory;
    private boolean autoCommit = true;
    private ExecutorType executorType = ExecutorType.SIMPLE;

    public WriteConnection(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean isAutoCommit() {
        return autoCommit;
    }

    public WriteConnection autoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
        return this;
    }

    @Override
    public ExecutorType getExecutorType() {
        return executorType;
    }

    public WriteConnection executorType(ExecutorType executorType) {
        this.executorType = executorType;
        return this;
    }

    @Override
    public WriteSession session() {
        return new WriteSession(this);
    }

}
