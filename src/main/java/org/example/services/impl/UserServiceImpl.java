package org.example.services.impl;

import org.example.dao.impl.UserDAO;
import org.example.entities.User;
import org.example.services.UserService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    private UserValidation userValidation;
    @Autowired
    public void setUserValidation(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    @Override
    public List<User> findAll() {
        return userDAO.readAll();
    }

    @Override
    public User findById(Long id) {
        return userDAO.readById(id);
    }

    @Override
    public User save(User user) {
        if (userValidation.isValidForCreate(user)){
            User savedUser = userDAO.create(user);
            return savedUser;
        } else{
            throw new ValidatorException("Invalid user to create");
        }
    }

    @Override
    public User update(User user) {
        if (userValidation.isValidForUpdate(user)){
            return userDAO.update(user);
        } else {
            throw new ValidatorException("Invalid user to update!");
        }
    }

    @Override
    public Boolean delete(Long id) {
        return userDAO.deleteById(id);
    }
}
