package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.Training;
import org.example.utils.DataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class TrainingDAO implements BaseDao<Training> {

    private final DataSource dataSource;

    public TrainingDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Training> readAll() {
        Map<Long, Training> trainingMap = dataSource.getTrainings();
        List<Training> trainings = new ArrayList<>(trainingMap.values());
        return trainings;
    }

    @Override
    public Training readById(Long id) {
        Map<Long, Training> trainingMap = dataSource.getTrainings();
        return trainingMap.get(id);
    }

    @Override
    public Training create(Training training) {
        Long id = getLastId()+1;
        Map<Long, Training> trainingMap = dataSource.getTrainings();
        training.setId(id);
        trainingMap.put(id, training);
        return trainingMap.get(id);
    }

    @Override
    public Training update(Training training) {
        Long id = training.getId();
        Map<Long, Training> trainingMap = dataSource.getTrainings();
        if (existById(id)){
            trainingMap.put(id, training);
            return trainingMap.get(id);
        }
        throw new RuntimeException();
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, Training> trainingMap = dataSource.getTrainings();
        return trainingMap.containsKey(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        Map<Long, Training> trainingMap = dataSource.getTrainings();
        if (existById(id)){
            trainingMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Long getLastId() {
        TreeMap<Long, Training> trainings = (TreeMap) dataSource.getTrainings();
        return trainings.lastKey();
    }
}
