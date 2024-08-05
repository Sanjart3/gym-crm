package org.example.utils.validation.impl;


import org.example.entities.Trainee;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class TraineeValidation implements Validator<Trainee> {
    @Override
    public Boolean isValidForCreate(Trainee trainee) {
        if (trainee.getAddress()==null){
            throw new ValidatorException("Address is required!");
        } else if (trainee.getDateOfBirth()==null) {
            throw new ValidatorException("Date of birth is required!");
        } else if (trainee.getUser()==null) {
            throw new ValidatorException("UserId is required!");
        }
        return true;
    }

    @Override
    public Boolean isValidForUpdate(Trainee trainee) {
        if (trainee.getId()==null){
            throw new ValidatorException("Id is required!");
        } else if (trainee.getDateOfBirth()==null){
            throw new ValidatorException("Date of birth is required!");
        } else if (trainee.getUser()==null){
            throw new ValidatorException("UserId is required!");
        } else if (trainee.getAddress()==null){
            throw new ValidatorException("Address is required!");
        }
        return true;
    }
}
