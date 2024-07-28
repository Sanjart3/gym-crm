package org.example.services;

import org.example.dao.impl.TrainingDAO;
import org.example.entities.Training;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {

    @Autowired
    private TrainingDAO trainingDAO;
    private TrainingValidation trainingValidation;
    @Autowired
    public void setTrainingValidation(TrainingValidation trainingValidation) {
        this.trainingValidation = trainingValidation;
    }

    public List<Training> findAll() {
        List<Training> trainingList = trainingDAO.readAll();
        return trainingList;
    }

    public Training findById(Long id) {
        Training training = trainingDAO.readById(id);
        return training;
    }

    public Training save(Training training) {
        if (trainingValidation.isValidForCreate(training)){
            Training savedTraining = trainingDAO.create(training);
            return savedTraining;
        } else {
            throw new ValidatorException("Invalid Training to create");
        }
    }
}
