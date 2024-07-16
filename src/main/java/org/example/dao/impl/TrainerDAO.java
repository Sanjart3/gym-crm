package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.Trainer;
import org.example.utils.DataSource;
import org.example.utils.exception.TrainerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDAO implements BaseDao<Trainer> {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Trainer> readAll() {
        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
        List<Trainer> trainers = new ArrayList<>(trainerMap.values());
        return trainers;
    }

    @Override
    public Trainer readById(Long id) {
        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
        return trainerMap.get(id);
    }

    @Override
    public Trainer create(Trainer trainer) {
        Long id = getLastId()+1;
        trainer.setId(id);
        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
        trainerMap.put(id, trainer);
        return trainerMap.get(id);
    }

    @Override
    public Trainer update(Trainer trainer) {
        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
        Long id = trainer.getId();
        if (trainerMap.containsKey(id)){
            trainerMap.put(id, trainer);
            return trainerMap.get(id);
        }
        throw new TrainerNotFoundException("Trainer not found");
    }

    @Override
    public Boolean existById(Long id) {
        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
        return trainerMap.containsKey(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
        if (trainerMap.containsKey(id)) {
            trainerMap.remove(id);
            return true;
        }
        throw new TrainerNotFoundException("Trainer with id " + id + " not found");
    }

    @Override
    public Long getLastId() {
        TreeMap<Long, Trainer> trainerMap = (TreeMap) dataSource.getTrainers();
        Long lastId = trainerMap.lastKey();
        return lastId;
    }
}
