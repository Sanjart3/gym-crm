package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.TrainingType;
import org.example.utils.DataSource;
import org.example.utils.exception.TrainingNotFoundException;
import org.example.utils.exception.TrainingTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class TrainingTypeDAO implements BaseDao<TrainingType> {

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
        Long id = getLastId()+1;
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        trainingType.setId(id);
        trainingTypeMap.put(id, trainingType);
        return trainingType;
    }

    @Override
    public TrainingType update(TrainingType trainingType) {
        Long id = trainingType.getId();
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        if (trainingTypeMap.containsKey(id)) {
            trainingTypeMap.put(id, trainingType);
            return trainingType;
        }
        throw new TrainingTypeNotFoundException(id);
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        return trainingTypeMap.containsKey(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        Map<Long, TrainingType> trainingTypeMap = dataSource.getTrainingTypes();
        if (trainingTypeMap.containsKey(id)) {
            trainingTypeMap.remove(id);
            return true;
        }
        throw new TrainingTypeNotFoundException(id);
    }

    @Override
    public Long getLastId() {
        TreeMap<Long, TrainingType> trainingTypeMap = (TreeMap) dataSource.getTrainingTypes();
        Long lastId = trainingTypeMap.lastKey();
        return lastId;
    }
}
