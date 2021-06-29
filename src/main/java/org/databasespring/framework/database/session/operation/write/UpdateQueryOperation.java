package org.databasespring.framework.database.session.operation.write;

import org.databasespring.framework.database.connection.DatabaseConnection;
import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.session.operation.AbstractQueryOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

public class UpdateQueryOperation extends AbstractQueryOperation<UpdateQueryOperation, Integer> {

    private final String statement;
    private final Object parameter;

    public UpdateQueryOperation(String statement, Object parameter, DatabaseConnection databaseConnection) {
        super(databaseConnection);
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
    public Integer execute(SqlSession sqlSession) throws DatabaseException {
        if (StringUtils.isEmpty(statement)) {
            throw new DatabaseException("Invalid statement provided", statement);
        }

        int affectedRecords;
        try {
            if (parameter != null) {
                affectedRecords = sqlSession.update(statement, parameter);
            } else {
                affectedRecords = sqlSession.update(statement);
            }
        } catch (PersistenceException e) {
            throw new DatabaseException("Unable to execute query", statement, e);
        }

        return affectedRecords;
    }

}
