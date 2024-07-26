package org.example.services;

import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TraineeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService{

    @Autowired
    private TraineeDAO traineeDAO;
    private TraineeValidation traineeValidation;
    @Autowired
    public void setTraineeValidation(TraineeValidation traineeValidation) {
        this.traineeValidation = traineeValidation;
    }

    public List<Trainee> findAll() {
        List<Trainee> trainees = traineeDAO.readAll();
        return trainees;
    }

    public Trainee findById(Long id) {
        Trainee trainee = traineeDAO.readById(id);
        return trainee;
    }

    public Trainee save(Trainee trainee) {
        if (traineeValidation.isValidForCreate(trainee)){
            Trainee savedTrainee = traineeDAO.create(trainee);
            return savedTrainee;
        } else {
            throw new ValidatorException("Invalid trainee to create");
        }
    }

    public Trainee update(Trainee trainee) {
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
}
