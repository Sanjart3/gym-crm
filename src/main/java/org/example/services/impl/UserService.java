package org.example.services.impl;

import org.example.dao.impl.UserDAO;
import org.example.entities.User;
import org.example.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements BaseService<User> {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> findAll() {
        List<User> users = userDAO.readAll();
        return users;
    }

    @Override
    public User findById(Long id) {
        User user = userDAO.readById(id);
        return user;
    }

    @Override
    public User save(User user) {
        User savedUser = userDAO.create(user);
        return savedUser;
    }

    @Override
    public User update(User user) {
        User updatedUser = userDAO.update(user);
        return updatedUser;
    }

    @Override
    public Boolean delete(Long id) {
        return userDAO.deleteById(id);
    }
}
