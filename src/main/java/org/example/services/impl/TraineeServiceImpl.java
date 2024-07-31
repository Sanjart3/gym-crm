package org.example.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.services.TraineeService;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TraineeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LogManager.getLogger(TraineeServiceImpl.class);
    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private TraineeValidation traineeValidation;

    @Override
    public List<Trainee> findAll() {
        return traineeDAO.readAll();
    }

    @Override
    public Trainee findById(Long id) {
        return traineeDAO.readById(id);
    }

    @Override
    public Trainee save(Trainee trainee) {
        if (traineeValidation.isValidForCreate(trainee)) {
            Trainee createdTrainee = traineeDAO.create(trainee);
            LOGGER.info("Trainee created: {}", createdTrainee);
            return traineeDAO.create(trainee);
        } else {
            LOGGER.warn("Trainee not created: {}", trainee);
            throw new ValidatorException("Invalid trainee to create");
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (traineeDAO.existById(trainee.getId())) {
            if (traineeValidation.isValidForUpdate(trainee)) {
                Trainee updatedTrainee = traineeDAO.update(trainee);
                LOGGER.info("Trainee updated: {}", updatedTrainee);
                return traineeDAO.update(trainee);
            }
            LOGGER.warn("Trainee not updated: {}", trainee);
            throw new ValidatorException("Invalid trainee to update");
        }
        LOGGER.error("Trainee not found with id: {}", trainee.getId());
        throw new TraineeNotFoundException(trainee.getId());
    }

    @Override
    public Boolean delete(Long id) {
        if (traineeDAO.existById(id)){
            traineeDAO.deleteById(id);
            LOGGER.info("Trainee deleted: {}", id);
            return true;
        }
        LOGGER.warn("Trainee not found: {}", id);
        throw new TraineeNotFoundException(id);
    }
}
