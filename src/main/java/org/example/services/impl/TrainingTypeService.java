package org.example.services.impl;

import org.example.dao.impl.TrainingTypeDAO;
import org.example.entities.TrainingType;
import org.example.services.BaseService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainingTypeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService implements BaseService<TrainingType> {

    @Autowired
    private TrainingTypeDAO trainingTypeDAO;
    private TrainingTypeValidation trainingTypeValidation;
    @Autowired
    public void setTrainingTypeValidation(TrainingTypeValidation trainingTypeValidation) {
        this.trainingTypeValidation = trainingTypeValidation;
    }

    @Override
    public List<TrainingType> findAll() {
        List<TrainingType> trainingTypes = trainingTypeDAO.readAll();
        return trainingTypes;
    }

    @Override
    public TrainingType findById(Long id) {
        TrainingType trainingType = trainingTypeDAO.readById(id);
        return trainingType;
    }

    @Override
    public TrainingType save(TrainingType trainingType) {
        if (trainingTypeValidation.isValidForCreate(trainingType)){
            TrainingType savedTrainingType = trainingTypeDAO.create(trainingType);
            return savedTrainingType;
        } else {
            throw new ValidatorException("Invalid training type to create");
        }
    }

    @Override
    public TrainingType update(TrainingType trainingType) {
        if (trainingTypeValidation.isValidForUpdate(trainingType)){
            TrainingType updatedTrainingType = trainingTypeDAO.update(trainingType);
            return updatedTrainingType;
        } else {
            throw new ValidatorException("Invalid training type to update");
        }
    }

    @Override
    public Boolean delete(Long id) {
        return trainingTypeDAO.deleteById(id);
    }
}
