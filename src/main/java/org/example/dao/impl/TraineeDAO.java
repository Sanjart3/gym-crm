package org.example.dao.impl;


import org.example.dao.BaseDao;
import org.example.entities.Trainee;
import org.example.utils.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class TraineeDAO implements BaseDao<Trainee> {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Trainee> readAll() {
        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
        return new ArrayList<>(allTrainee.values());
    }

    @Override
    public Trainee readById(Long id) {
        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
        Trainee trainee = allTrainee.get(id);
        return trainee;
    }

    @Override
    public Trainee create(Trainee trainee) {
        Long id = getLastId()+1;
        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
        trainee.setId(id);
        allTrainee.put(id, trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        Long id = trainee.getId();
        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
        allTrainee.put(id, trainee);
        return trainee;
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
        return allTrainee.containsKey(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        Map<Long, Trainee> traineeMap = dataSource.getTrainees();
        traineeMap.remove(id);
        return true;
    }

    @Override
    public Long getLastId() {
        TreeMap<Long, Trainee> traineeMap = (TreeMap) dataSource.getTrainees();
        Long lastId = traineeMap.lastKey();
        return lastId;
    }

}
