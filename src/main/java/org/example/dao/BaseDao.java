package org.example.dao;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseDao<T> {
    List<T> readAll();
    T readById(Long id);
    T create(T t);
    T update(T t);
    Boolean existById(Long id);
    Boolean deleteById(Long id);
    Long getLastId();
}
