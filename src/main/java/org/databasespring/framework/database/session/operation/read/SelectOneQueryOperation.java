package org.databasespring.framework.database.session.operation.read;

import org.databasespring.framework.database.connection.DatabaseConnection;
import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.session.operation.AbstractQueryOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class SelectOneQueryOperation<T> extends AbstractQueryOperation<SelectOneQueryOperation<T>, T> {

    private final Class<T> resultClazz;
    private final String statement;
    private final Object parameter;

    public SelectOneQueryOperation(Class<T> resultClazz, String statement, Object parameter, DatabaseConnection databaseConnection) {
        super(databaseConnection);
        this.resultClazz = resultClazz;
        this.statement = statement;
        this.parameter = parameter;
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public Object getParameter() {
        return parameter;
    }

    @Override
    public RowBounds getRowBounds() {
        return null;
    }

    @Override
    public T execute(SqlSession sqlSession) throws DatabaseException {
        if (StringUtils.isEmpty(statement)) {
            throw new DatabaseException("Invalid statement provided", statement);
        }

        Object rawResult;
        try {
            if (parameter != null) {
                rawResult = sqlSession.selectOne(statement, parameter);
            } else {
                rawResult = sqlSession.selectOne(statement);
            }
        } catch (Exception e) {
            throw new DatabaseException("Unable to execute query", statement, e);
        }

        if (rawResult == null) {
            return null;
        }

        if (rawResult.getClass().isAssignableFrom(resultClazz)) {
            return resultClazz.cast(rawResult);
        }

        throw new DatabaseException("Raw result and result class don't match");
    }
}
