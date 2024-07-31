package org.example.services;

import org.example.dao.impl.TrainerDAO;
import org.example.entities.Trainer;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerDAO trainerDAO;
    private TrainerValidation trainerValidation;
    @Autowired
    public void setTrainerValidation(TrainerValidation trainerValidation) {
        this.trainerValidation = trainerValidation;
    }

    public List<Trainer> findAll() {
        List<Trainer> trainers = trainerDAO.readAll();
        return trainers;
    }

    public Trainer findById(Long id) {
        Trainer trainer = trainerDAO.readById(id);
        return trainer;
    }

    public Trainer save(Trainer trainer) {
        if (trainerValidation.isValidForCreate(trainer)){
            Trainer savedTrainer = trainerDAO.create(trainer);
            return trainer;
        } else {
            throw new ValidatorException("Invalid trainer to create");
        }
    }

    public Trainer update(Trainer trainer) {
        if (trainerValidation.isValidForUpdate(trainer)){
            Trainer savedTrainer = trainerDAO.update(trainer);
            return savedTrainer;
        } else {
            throw new ValidatorException("Invalid trainer to update");
        }
    }
}
