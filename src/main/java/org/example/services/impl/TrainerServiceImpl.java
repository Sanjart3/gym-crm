package org.example.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.impl.TrainerDAO;
import org.example.entities.Trainer;
import org.example.services.TrainerService;
import org.example.utils.exception.TrainerNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDAO trainerDAO;
    private TrainerValidation trainerValidation;
    private static final Logger LOGGER = LogManager.getLogger(TrainerServiceImpl.class);
    @Autowired
    public void setTrainerValidation(TrainerValidation trainerValidation) {
        this.trainerValidation = trainerValidation;
    }

    @Override
    public Trainer findByUsername(String username) {
        try {
            return trainerDAO.findByUsername(username).get();
        } catch (TrainerNotFoundException e) {
            LOGGER.error("Trainer not found", e);
            throw e;
        }
    }

    @Override
    public void changePassword(String username, String newPassword) {
        Optional<Trainer> trainer = trainerDAO.changePassword(username, newPassword);
        if (trainer.isPresent()) {
            LOGGER.info("Password changed successfully!");
        } else {
            LOGGER.error("Password change failed!");
            throw new ValidatorException("Password change failed!");
        }
    }

    @Override
    public void changeStatus(String username, boolean status) {
        boolean isChanged = trainerDAO.changeStatus(username, status);
        if (isChanged){
            LOGGER.info("Status changed successfully!");
        } else {
            LOGGER.error("Status change failed!");
            throw new ValidatorException("Status change failed!");
        }
    }

    @Override
    public List<Trainer> findAll() {
        List<Trainer> trainers = trainerDAO.readAll();
        return trainers;
    }

    @Override
    public Trainer findById(Long id) {
        Trainer trainer = trainerDAO.readById(id);
        return trainer;
    }

    @Override
    public Trainer save(Trainer trainer) {
        try{
            trainerValidation.isValidForCreate(trainer);  //checks for validation, and throws exception for invalid parameters
            Trainer savedTrainer = trainerDAO.create(trainer).get();
            LOGGER.info("Saved trainer " + savedTrainer);
            return savedTrainer;
        } catch (ValidatorException e){
            LOGGER.warn("Invalid trainer to save: {}", trainer, e);
            throw e;
        }
    }

    @Override
    public Trainer update(Trainer trainer) {
        if (trainerDAO.existById(trainer.getId())) {
            try {
                trainerValidation.isValidForUpdate(trainer);  //checks for validation, and throws exception for invalid parameters
                Trainer updatedTrainer = trainerDAO.update(trainer).get();
                LOGGER.info("Updated trainer " + updatedTrainer);
                return updatedTrainer;
            } catch (ValidatorException e){
                LOGGER.warn("Invalid trainer to update: " + trainer, e);
                throw e;
            }
        }
        LOGGER.error("Trainer with id: {} not found", trainer.getId());
        throw new TrainerNotFoundException(trainer.getId());
    }
}
