package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.Training;
import org.example.utils.DataSource;
import org.example.utils.exception.TrainingNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainingDAO implements BaseDao<Training> {

    private static SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Training> readAll() {
//        Map<Long, Training> trainingMap = dataSource.getTrainings();
//        List<Training> trainings = new ArrayList<>(trainingMap.values());
//        return trainings;
        return null;
    }

    @Override
    public Training readById(Long id) {
//        Map<Long, Training> trainingMap = dataSource.getTrainings();
//        return trainingMap.get(id);
        return null;
    }

    @Override
    public Training create(Training training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Training savedTraining = null;
        try{
            transaction = session.beginTransaction();
            Long trainingId = (Long) session.save(training);
            transaction.commit();
            savedTraining = (Training) session.get(Training.class, trainingId);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return savedTraining;
    }

    @Override
    public Training update(Training training) {
//        Long id = training.getId();
//        Map<Long, Training> trainingMap = dataSource.getTrainings();
//        if (existById(id)){
//            trainingMap.put(id, training);
//            return trainingMap.get(id);
//        }
//        throw new TrainingNotFoundException(id);
        return null;
    }

    @Override
    public Boolean existById(Long id) {
//        Map<Long, Training> trainingMap = dataSource.getTrainings();
//        return trainingMap.containsKey(id);
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
//        Map<Long, Training> trainingMap = dataSource.getTrainings();
//        if (trainingMap.containsKey(id)){
//            trainingMap.remove(id);
//            return true;
//        }
        throw new TrainingNotFoundException(id);
    }
}
