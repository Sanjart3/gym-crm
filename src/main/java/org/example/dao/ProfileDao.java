package org.example.dao;

public interface ProfileDao<T> extends CRDao<T> {
    T update(T t);
    T findByUsername(String username);
    Boolean deleteByUsername(String username);
}
