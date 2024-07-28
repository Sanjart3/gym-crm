package org.example.services;

import org.example.dao.impl.TrainerDAO;
import org.example.dto.TrainerRequest;
import org.example.entities.Trainer;
import org.example.entities.User;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TrainerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService{

    @Autowired
    private TrainerDAO trainerDAO;
    private TrainerValidation trainerValidation;
    private UserService userService;
    @Autowired
    public void setTrainerValidation(TrainerValidation trainerValidation) {
        this.trainerValidation = trainerValidation;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Trainer> findAll() {
        List<Trainer> trainers = trainerDAO.readAll();
        return trainers;
    }

    public Trainer findById(Long id) {
        Trainer trainer = trainerDAO.readById(id);
        return trainer;
    }

    public Trainer save(TrainerRequest trainerRequest) {
        User user = getUser(trainerRequest);
        User savedUser = userService.save(user);
        Trainer trainer = getTrainer(trainerRequest,savedUser);
        if (trainerValidation.isValidForCreate(trainer)){
            Trainer savedTrainer = trainerDAO.create(trainer);
            return savedTrainer;
        } else {
            throw new ValidatorException("Invalid trainer to create");
        }
    }

    public Trainer update(TrainerRequest trainerRequest) {
        User user = getUser(trainerRequest);
        User updatedUser = userService.save(user);
        Trainer trainer = getTrainer(trainerRequest,updatedUser);
        if (trainerValidation.isValidForUpdate(trainer)){
            Trainer savedTrainer = trainerDAO.update(trainer);
            return savedTrainer;
        } else {
            throw new ValidatorException("Invalid trainer to update");
        }
    }

    public Trainer getTrainer(TrainerRequest trainerRequest, User user) {
        Long trainingType = trainerRequest.getTrainingTypeId();
        return new Trainer(trainingType, user.getId());
    }

    public User getUser(TrainerRequest trainerRequest) {
        String firstName = trainerRequest.getFirstName();
        String lastName = trainerRequest.getLastName();
        Boolean isActive = trainerRequest.getActive();
        User user = new User(firstName, lastName, isActive);
        return user;
    }
}
