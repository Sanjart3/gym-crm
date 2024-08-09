package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface CRDao<T> {
    List<T> readAll();
    T readById(Long id);
    Optional<T> create(T t);
    Boolean existById(Long id);
}
