package org.example.services;

import org.example.dao.impl.UserDAO;
import org.example.entities.User;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;
    private UserValidation userValidation;
    public void setUserValidation(UserValidation userValidation) {
        this.userValidation = userValidation;
    }

    public List<User> findAll() {
        return userDAO.readAll();
    }

    public User findById(Long id) {
        return userDAO.readById(id);
    }

    public User save(User user) {
        if (userValidation.isValidForCreate(user)){
            User savedUser = userDAO.create(user);
            return savedUser;
        } else{
            throw new ValidatorException("Invalid user to create");
        }
    }

    public User update(User user) {
        if (userValidation.isValidForUpdate(user)){
            return userDAO.update(user);
        } else {
            throw new ValidatorException("Invalid user to update!");
        }
    }

    public Boolean delete(Long id) {
        return userDAO.deleteById(id);
    }
}
