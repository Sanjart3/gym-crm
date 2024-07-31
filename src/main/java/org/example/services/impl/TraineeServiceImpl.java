package org.example.services.impl;

import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.services.TraineeService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.TraineeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {

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
            return traineeDAO.create(trainee);
        } else {
            throw new ValidatorException("Invalid trainee to create");
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        if (traineeValidation.isValidForUpdate(trainee)) {
            return traineeDAO.update(trainee);
        } else {
            throw new ValidatorException("Invalid trainee to update");
        }
    }

    @Override
    public Boolean delete(Long id) {
        return traineeDAO.deleteById(id);
    }
}
