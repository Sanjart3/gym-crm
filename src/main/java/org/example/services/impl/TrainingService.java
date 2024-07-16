package org.example.services.impl;

import org.example.dao.impl.TrainingDAO;
import org.example.entities.Training;
import org.example.services.BaseService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements BaseService<Training> {

    @Autowired
    private TrainingDAO trainingDAO;
    private TrainingValidation trainingValidation;
    @Autowired
    public void setTrainingValidation(TrainingValidation trainingValidation) {
        this.trainingValidation = trainingValidation;
    }

    @Override
    public List<Training> findAll() {
        List<Training> trainingList = trainingDAO.readAll();
        return trainingList;
    }

    @Override
    public Training findById(Long id) {
        Training training = trainingDAO.readById(id);
        return training;
    }

    @Override
    public Training save(Training training) {
        if (trainingValidation.isValidForCreate(training)){
            Training savedTraining = trainingDAO.create(training);
            return savedTraining;
        } else {
            throw new ValidatorException("Invalid Training to create");
        }
    }

    @Override
    public Training update(Training training) {
        if (trainingValidation.isValidForUpdate(training)){
            Training updatedTraining = trainingDAO.update(training);
            return updatedTraining;
        } else {
            throw new ValidatorException("Invalid Training to update");
        }
    }

    @Override
    public Boolean delete(Long id) {
        return trainingDAO.deleteById(id);
    }
}
