package org.example.utils.validation.impl;

import org.example.entities.User;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class UserValidation implements Validator<User> {
    @Override
    public Boolean isValidForCreate(User user) {
        if (user.getFirstName() == null) {
            throw new ValidatorException("First name is required!");
        } else if (user.getLastName() == null) {
            throw new ValidatorException("Last name is required!");
        } else if (user.getActive() == null) {
            throw new ValidatorException("Active status is required!");
        }
        return true;
    }

    @Override
    public Boolean isValidForUpdate(User user) {
        if (user.getId()==null){
            throw new ValidatorException("id is required!");
        } else if (user.getFirstName() == null) {
            throw new ValidatorException("First name is required!");
        } else if (user.getLastName() == null) {
            throw new ValidatorException("Last name is required!");
        } else if (user.getUsername()==null) {
            throw new ValidatorException("username is required!");
        } else if (user.getActive() == null) {
            throw new ValidatorException("Active status is required!");
        } else if (user.getPassword() == null) {
            throw new ValidatorException("Password is required!");
        }
        return true;
    }
}
