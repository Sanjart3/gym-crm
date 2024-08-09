package org.example.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.entities.Trainer;
import org.example.services.TraineeService;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TraineeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LogManager.getLogger(TraineeServiceImpl.class);
    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private TraineeValidation traineeValidation;

    @Override
    public Trainee findByUsername(String username) {
        Optional<Trainee> trainee = traineeDAO.findByUsername(username);
        if (trainee.isPresent()) {
            return trainee.get();
        } else {
            LOGGER.error("Trainee not found");
            throw new TraineeNotFoundException("Trainee with username " + username + " not found");
        }
    }

    @Override
    public void passwordChange(String username, String newPassword) {
        Optional<Trainee> trainee = traineeDAO.changePassword(username, newPassword);
        if (trainee.isPresent()) {
            LOGGER.info("Password changed successfully!");
        } else {
            LOGGER.error("Password change failed!");
            throw new TraineeNotFoundException("Trainee with username " + username + " not found");
        }
    }

    @Override
    public void changeStatus(String username, boolean status) {
        boolean trainee = traineeDAO.changeStatus(username, status);
        if (trainee){
            LOGGER.info("Trainee status changed successfully!");
        } else {
            LOGGER.error("Trainee status change failed!");
        }
    }

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
        try {
            traineeValidation.isValidForCreate(trainee);  //checks for validation, and throws exception for invalid parameters
            Trainee createdTrainee = traineeDAO.create(trainee).get();
            LOGGER.info("Trainee created: {}", createdTrainee);
            return createdTrainee;
        } catch (ValidatorException e) {
            LOGGER.warn("Trainee not created: {}", trainee, e);
            throw e;
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (traineeDAO.existById(trainee.getId())) {
            try{
                traineeValidation.isValidForUpdate(trainee); //checks for validation, and throws exception for invalid parameters
                Trainee updatedTrainee = traineeDAO.update(trainee).get();
                LOGGER.info("Trainee updated: {}", updatedTrainee);
                return updatedTrainee;
            }catch (ValidatorException e) {
                LOGGER.warn("Trainee not updated: {}", trainee, e);
                throw e;
            }
        }
        LOGGER.error("Trainee not found with id: {}", trainee.getId());
        throw new TraineeNotFoundException(trainee.getId());
    }

    @Override
    public Boolean deleteByUsername(String username) {
        try {
            return traineeDAO.deleteByUsername(username);
        } catch (TraineeNotFoundException e) {
            LOGGER.warn("Trainee not found with username: {}", username, e);
            throw e;
        }
    }



    @Override
    public Trainee addTrainer(Long traineeId, Trainer trainer) {
        try{
            return traineeDAO.addTrainerToTrainee(traineeId, trainer).get();
        } catch (TraineeNotFoundException e) {
            LOGGER.warn("Trainee not found with id: {}", traineeId, e);
            throw e;
        }
    }

    @Override
    public Trainee removeTrainer(Long traineeId, Trainer trainer) {
        try {
            return traineeDAO.removeTrainerFromTrainee(traineeId, trainer).get();
        } catch (TraineeNotFoundException e) {
            LOGGER.warn("Trainee not found with id: {}", traineeId, e);
            throw e;
        }
    }

    @Override
    public List<Trainer> findUnassignedTrainers(String username) {
        try {
            return traineeDAO.getUnassignedTrainersByTraineeUsername(username);
        } catch (TraineeNotFoundException e) {
            LOGGER.warn("Trainee not found with username: {}", username, e);
            throw e;
        }
    }


}
