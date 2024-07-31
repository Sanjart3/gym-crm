package org.example.utils.validation.impl;

import org.example.entities.TrainingType;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeValidation implements Validator<TrainingType> {
    @Override
    public Boolean isValidForCreate(TrainingType trainingType) {
        if (trainingType.getName()==null){
            throw new ValidatorException("Training type name can not be null");
        }
        return true;
    }

    @Override
    public Boolean isValidForUpdate(TrainingType trainingType) {
        if (trainingType.getId()==null){
            throw new ValidatorException("Training type id can not be null");
        } else if (trainingType.getName()==null){
            throw new ValidatorException("Training type name can not be null");
        }
        return true;
    }
}
