package org.example.utils.validation.impl;

import org.example.entities.Trainer;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class TrainerValidation implements Validator<Trainer> {
    @Override
    public Boolean isValidForCreate(Trainer trainer) {
        if (trainer.getUserId()==null){
            throw new ValidatorException("User is required!");
        } else if (trainer.getSpecialization()==null) {
            throw new ValidatorException("Specialization is required!");
        }
        return true;
    }

    @Override
    public Boolean isValidForUpdate(Trainer trainer) {
        if (trainer.getId()==null){
            throw new ValidatorException("Id is required!");
        } else if (trainer.getUserId()==null){
            throw new ValidatorException("User is required!");
        } else if (trainer.getSpecialization()==null){
            throw new ValidatorException("Specialization is required!");
        }
        return true;
    }
}
