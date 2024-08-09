package org.example.dao.impl;

import org.example.dao.CRDao;
import org.example.dao.TrainingDao;
import org.example.dto.CriteriaDto;
import org.example.entities.Training;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainingDAO extends TrainingDao {

    private static SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Training> readAll() {
        Session session = sessionFactory.openSession();
        List<Training> trainings = session.createQuery("from Training").list();
        session.close();
        return trainings;
    }

    @Override
    public Training readById(Long id) {
        Session session = sessionFactory.openSession();
        Training training = session.get(Training.class, id);
        session.close();
        return training;
    }

    @Override
    public Optional<Training> create(Training training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Training savedTraining = null;
        try{
            transaction = session.beginTransaction();
            Long trainingId = (Long) session.save(training);
            transaction.commit();
            savedTraining = session.get(Training.class, trainingId);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Optional.ofNullable(savedTraining);
    }
    @Override
    public Boolean existById(Long id) {
//        Map<Long, Training> trainingMap = dataSource.getTrainings();
//        return trainingMap.containsKey(id);
        return false;
    }

}
