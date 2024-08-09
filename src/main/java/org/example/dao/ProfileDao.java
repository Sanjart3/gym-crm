package org.example.dao;

import java.util.Optional;

public interface ProfileDao<T> extends CRDao<T> {
    Optional<T> update(T t);
    Optional<T> findByUsername(String username);
    Boolean deleteByUsername(String username);
    Boolean changeStatus(String username, Boolean status);

    Boolean changeStatus(String username, Boolean status, Class<T> entityClass);
    String getUserName(Class<T> entityClass, String baseUsername);
}
