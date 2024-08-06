package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.Trainer;
import org.example.utils.DataSource;
import org.example.utils.exception.TrainerNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDAO implements BaseDao<Trainer> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Trainer> readAll() {
        Session session = sessionFactory.openSession();
        List<Trainer> trainers = session.createCriteria(Trainer.class).list();
        session.close();
        return trainers;
    }

    @Override
    public Trainer readById(Long id) {
        Session session = sessionFactory.openSession();
        Trainer trainer = session.get(Trainer.class, id);
        session.close();
        return trainer;
    }

    @Override
    public Trainer create(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Trainer createdTrainer = new Trainer();
        try{
            transaction = session.beginTransaction();
            Long trainerId = (Long) session.save(trainer);
            transaction.commit();
            createdTrainer = session.get(Trainer.class, trainerId);
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return createdTrainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
//        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
//        Long id = trainer.getId();
//        trainerMap.put(id, trainer);
//        return trainerMap.get(id);
        return null;
    }

    @Override
    public Boolean existById(Long id) {
//        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
//        return trainerMap.containsKey(id);
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
//        Map<Long, Trainer> trainerMap = dataSource.getTrainers();
//        if (trainerMap.containsKey(id)) {
//            trainerMap.remove(id);
//            return true;
//        }
        throw new TrainerNotFoundException(id);
    }

}
