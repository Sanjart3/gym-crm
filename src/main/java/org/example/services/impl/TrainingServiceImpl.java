package org.example.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.impl.TrainingDAO;
import org.example.entities.Training;
import org.example.services.TrainingService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LogManager.getLogger(TrainingServiceImpl.class);
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
            LOGGER.info("Saved training: {}", savedTraining);
            return savedTraining;
        }
        LOGGER.warn("Invalid training: {}", training);
        throw new ValidatorException("Invalid Training to create");
    }
}
