package org.example.dao;

import java.util.Optional;

public interface ProfileDao<T> extends CRDao<T> {
    Optional<T> update(T t);
    Optional<T> findByUsername(String username);
    Boolean deleteByUsername(String username);
    Boolean changeStatus(String username, Boolean status);
}
