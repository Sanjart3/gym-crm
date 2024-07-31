package org.example.services.impl;

import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.services.BaseService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TraineeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService implements BaseService<Trainee> {

    @Autowired
    private TraineeDAO traineeDAO;
    private TraineeValidation traineeValidation;
    @Autowired
    public void setTraineeValidation(TraineeValidation traineeValidation) {
        this.traineeValidation = traineeValidation;
    }

    @Override
    public List<Trainee> findAll() {
        List<Trainee> trainees = traineeDAO.readAll();
        return trainees;
    }

    @Override
    public Trainee findById(Long id) {
        Trainee trainee = traineeDAO.readById(id);
        return trainee;
    }

    @Override
    public Trainee save(Trainee trainee) {
        if (traineeValidation.isValidForCreate(trainee)){
            Trainee savedTrainee = traineeDAO.create(trainee);
            return savedTrainee;
        } else {
            throw new ValidatorException("Invalid trainee to create");
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (traineeValidation.isValidForUpdate(trainee)){
            Trainee savedTrainee = traineeDAO.update(trainee);
            return savedTrainee;
        } else {
            throw new ValidatorException("Invalid trainee to update");
        }
    }

    @Override
    public Boolean delete(Long id) {
        return traineeDAO.deleteById(id);
    }
}
