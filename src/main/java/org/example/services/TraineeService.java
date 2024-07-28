package org.example.services;

import org.example.dao.impl.TraineeDAO;
import org.example.dto.TraineeRequest;
import org.example.entities.Trainee;
import org.example.entities.User;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TraineeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService{

    @Autowired
    private TraineeDAO traineeDAO;
    private UserService userService;
    private TraineeValidation traineeValidation;
    @Autowired
    public void setTraineeValidation(TraineeValidation traineeValidation) {
        this.traineeValidation = traineeValidation;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Trainee> findAll() {
        List<Trainee> trainees = traineeDAO.readAll();
        return trainees;
    }

    public Trainee findById(Long id) {
        Trainee trainee = traineeDAO.readById(id);
        return trainee;
    }

    public Trainee save(TraineeRequest traineeRequest) {
        User user = getUser(traineeRequest);
        User savedUser = userService.save(user);
        Trainee trainee = getTrainee(traineeRequest, savedUser);
        if (traineeValidation.isValidForCreate(trainee)){
            Trainee savedTrainee = traineeDAO.create(trainee);
            return savedTrainee;
        } else {
            throw new ValidatorException("Invalid trainee to create");
        }
    }

    public Trainee update(TraineeRequest traineeRequest) {
        User user = getUser(traineeRequest);
        User updatedUser = userService.update(user);
        Trainee trainee = getTrainee(traineeRequest, updatedUser);
        if (traineeValidation.isValidForUpdate(trainee)){
            Trainee savedTrainee = traineeDAO.update(trainee);
            return savedTrainee;
        } else {
            throw new ValidatorException("Invalid trainee to update");
        }
    }

    public Boolean delete(Long id) {
        return traineeDAO.deleteById(id);
    }

    public Trainee getTrainee(TraineeRequest traineeRequest, User user) {
        Trainee trainee = new Trainee(traineeRequest.getDateOfBirth(), traineeRequest.getAddress(), user.getId());
        return trainee;
    }

    public User getUser(TraineeRequest traineeRequest) {
        String firstName = traineeRequest.getFirstName();
        String lastName = traineeRequest.getLastName();
        Boolean isActive = traineeRequest.getActive();
        User user = new User(firstName, lastName, isActive);
        return user;
    }
}
