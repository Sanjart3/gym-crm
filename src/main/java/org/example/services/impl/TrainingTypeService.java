package org.example.services.impl;

import org.example.dao.impl.TrainingTypeDAO;
import org.example.entities.TrainingType;
import org.example.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService implements BaseService<TrainingType> {

    @Autowired
    private TrainingTypeDAO trainingTypeDAO;

    @Override
    public List<TrainingType> findAll() {
        List<TrainingType> trainingTypes = trainingTypeDAO.readAll();
        return trainingTypes;
    }

    @Override
    public TrainingType findById(Long id) {
        TrainingType trainingType = trainingTypeDAO.readById(id);
        return trainingType;
    }

    @Override
    public TrainingType save(TrainingType trainingType) {
        TrainingType savedTrainingType = trainingTypeDAO.create(trainingType);
        return savedTrainingType;
    }

    @Override
    public TrainingType update(TrainingType trainingType) {
        TrainingType updatedTrainingType = trainingTypeDAO.update(trainingType);
        return updatedTrainingType;
    }

    @Override
    public Boolean delete(Long id) {
        return trainingTypeDAO.deleteById(id);
    }
}
