package org.example.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.impl.UserDAO;
import org.example.entities.User;
import org.example.services.UserService;
import org.example.utils.exception.UserNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
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
        try {
            userValidation.isValidForCreate(user); //checks for validation, and throws exception for invalid parameters
            User savedUser = userDAO.create(user);
            LOGGER.info("Saved user: {}", savedUser);
            return savedUser;
        } catch (ValidatorException e) {
            LOGGER.warn("Invalid user to create: {}", user, e);
            throw e;
        }
    }

    @Override
    public User update(User user) {
        if (userDAO.existById(user.getId())) {
            try{
                userValidation.isValidForUpdate(user);  //checks for validation, and throws exception for invalid parameters
                User updatedUser = userDAO.update(user);
                LOGGER.info("Updated user: {}", updatedUser);
                return updatedUser;
            } catch (ValidatorException e) {
                LOGGER.warn("Invalid user to update: {}", user, e);
                throw e;
            }
        }
        LOGGER.error("User with id {} not found", user.getId());
        throw new UserNotFoundException(user.getId());
    }

    @Override
    public Boolean delete(Long id) {
        if (userDAO.existById(id)){
            userDAO.deleteById(id);
            LOGGER.info("Deleted user: {}", id);
            return true;
        }
        LOGGER.error("User with id {} not found", id);
        throw new UserNotFoundException(id);
    }
}
