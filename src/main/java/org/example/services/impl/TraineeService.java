package org.example.services.impl;

import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService implements BaseService<Trainee> {

    @Autowired
    private TraineeDAO traineeDAO;


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
        Trainee savedTrainee = traineeDAO.create(trainee);
        return savedTrainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        Trainee savedTrainee = traineeDAO.update(trainee);
        return savedTrainee;
    }

    @Override
    public Boolean delete(Long id) {
        return traineeDAO.deleteById(id);
    }
}
