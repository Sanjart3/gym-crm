package org.example.services;

import org.example.entities.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User findById(Long id);
    public User save(User user);
    public User update(User user);
    public Boolean delete(Long id);
}
