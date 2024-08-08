package org.example.dao.impl;

import org.example.dao.CRDao;
import org.example.entities.TrainingType;
import org.example.utils.DataSource;
import org.example.utils.exception.TrainingTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingTypeDAO implements CRDao<TrainingType> {

    private DataSource dataSource;
    @Autowired
    public void setDatasource(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public List<TrainingType> readAll() {
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        List<TrainingType> trainingTypes = new ArrayList<>(trainingTypeMap.values());
        return trainingTypes;
    }

    @Override
    public TrainingType readById(Long id) {
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        TrainingType trainingType = trainingTypeMap.get(id);
        return trainingType;
    }

    @Override
    public TrainingType create(TrainingType trainingType) {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        return trainingTypeMap.containsKey(id);
    }
}
