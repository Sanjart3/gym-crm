package org.example.services;


import java.util.List;

public interface BaseService<T> {
    List<T> findAll();
    T findById(Long id);
    T save(T t);
    T update(T t);
    Boolean delete(Long id);
}
