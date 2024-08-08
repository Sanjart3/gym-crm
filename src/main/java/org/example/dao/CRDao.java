package org.example.dao;

import java.util.List;

public interface CRDao<T> {
    List<T> readAll();
    T readById(Long id);
    T create(T t);
    Boolean existById(Long id);
}
