package org.databasespring.framework.database.session;

import org.databasespring.framework.database.exception.DatabaseException;

import org.apache.ibatis.session.SqlSession;

@FunctionalInterface
public interface CustomSessionManager<T> {

    T apply(SqlSession sqlSession) throws DatabaseException;

}
