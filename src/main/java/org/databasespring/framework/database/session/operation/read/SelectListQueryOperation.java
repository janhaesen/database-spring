package org.databasespring.framework.database.session.operation.read;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.databasespring.framework.database.connection.DatabaseConnection;
import org.databasespring.framework.database.exception.DatabaseException;
import org.databasespring.framework.database.session.operation.AbstractQueryOperation;

public class SelectListQueryOperation<T> extends AbstractQueryOperation<SelectListQueryOperation<T>, List<T>> {

    private final Class<T> resultClazz;
    private final String statement;
    private final Object parameter;
    private final RowBounds rowBounds;

    public SelectListQueryOperation(Class<T> resultClazz, String statement, Object parameter, RowBounds rowBounds, DatabaseConnection databaseConnection) {
        super(databaseConnection);
        this.resultClazz = resultClazz;
        this.statement = statement;
        this.parameter = parameter;
        this.rowBounds = rowBounds;
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
        return rowBounds;
    }

    @Override
    public List<T> execute(SqlSession sqlSession) throws DatabaseException {
        if (StringUtils.isEmpty(statement)) {
            throw new DatabaseException("Invalid statement provided", statement);
        }

        List<T> rawResult;
        try {
            if (parameter != null && rowBounds != null) {
                rawResult = sqlSession.selectList(statement, parameter, rowBounds);
            } else if (parameter != null) {
                rawResult = sqlSession.selectList(statement, parameter);
            } else if (rowBounds != null) {
                rawResult = sqlSession.selectList(statement, null, rowBounds);
            } else {
                rawResult = sqlSession.selectList(statement);
            }
        } catch (PersistenceException e) {
            throw new DatabaseException("Unable to execute query", statement, e);
        }

        if (CollectionUtils.isEmpty(rawResult)) {
            return Collections.emptyList();
        }

        if (! rawResult.get(0).getClass().isAssignableFrom(resultClazz)) {
            throw new DatabaseException("Raw result and result class don't match");
        }

        return Collections.checkedList(rawResult, resultClazz);
    }
}
