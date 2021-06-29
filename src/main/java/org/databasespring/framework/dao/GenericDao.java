package org.databasespring.framework.dao;

public interface GenericDao<T> {

    T findById(long id);

    T find(Specification specification);

    T find();

    T create(T item);

    T update(T item);

    T delete(T item);

}
