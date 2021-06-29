package org.databasespring.framework.database.mapper;

public class DatabaseMapperWrapper<T extends DatabaseMapper> {

    private final T databaseMapper;

    public DatabaseMapperWrapper(T databaseMapper) {
        this.databaseMapper = databaseMapper;
    }

    public T getDatabaseMapper() {
        return databaseMapper;
    }
}
