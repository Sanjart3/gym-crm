package org.example.dao;

import java.util.Optional;

public interface ProfileDao<T> extends CRDao<T> {
    T update(T t);
    T findByUsername(String username);
    Boolean deleteByUsername(String username);
}
