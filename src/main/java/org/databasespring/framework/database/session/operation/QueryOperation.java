package org.databasespring.framework.database.session.operation;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.databasespring.framework.database.exception.DatabaseException;

public interface QueryOperation<T> {

    String getStatement();

    Object getParameter();

    RowBounds getRowBounds();

    T execute(SqlSession sqlSession) throws DatabaseException;

}
