package org.example.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.impl.TrainerDAO;
import org.example.entities.Trainer;
import org.example.entities.User;
import org.example.services.TrainerService;
import org.example.services.UserService;
import org.example.utils.exception.TrainerNotFoundException;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDAO trainerDAO;
    @Autowired
    private UserService userService;
    private TrainerValidation trainerValidation;
    private static final Logger LOGGER = LogManager.getLogger(TrainerServiceImpl.class);
    @Autowired
    public void setTrainerValidation(TrainerValidation trainerValidation) {
        this.trainerValidation = trainerValidation;
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
            User savedUser = userService.save(trainer.getUser());
            trainer.setUser(savedUser);
            Trainer savedTrainer = trainerDAO.create(trainer);
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
                Trainer updatedTrainer = trainerDAO.update(trainer);
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
