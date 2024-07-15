package org.example.services.impl;

import org.example.dao.impl.TrainingDAO;
import org.example.entities.Training;
import org.example.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements BaseService<Training> {

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public List<Training> findAll() {
        List<Training> trainingList = trainingDAO.readAll();
        return trainingList;
    }

    @Override
    public Training findById(Long id) {
        Training training = trainingDAO.readById(id);
        return training;
    }

    @Override
    public Training save(Training training) {
        Training savedTraining = trainingDAO.create(training);
        return savedTraining;
    }

    @Override
    public Training update(Training training) {
        Training updatedTraining = trainingDAO.update(training);
        return updatedTraining;
    }

    @Override
    public Boolean delete(Long id) {
        return trainingDAO.deleteById(id);
    }
}
