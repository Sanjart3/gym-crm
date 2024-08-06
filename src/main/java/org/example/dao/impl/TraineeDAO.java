package org.example.dao.impl;


import org.example.dao.BaseDao;
import org.example.entities.Trainee;
import org.example.utils.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class TraineeDAO implements BaseDao<Trainee> {

    private static SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Trainee> readAll() {
        Session session = sessionFactory.openSession();
        List<Trainee> traineeList = session.createQuery("from Trainee").list();
        session.close();
        return traineeList;
    }

    @Override
    public Trainee readById(Long id) {
        Session session = sessionFactory.openSession();
        Trainee trainee = session.get(Trainee.class, id);
        session.close();
        return trainee;
    }

    @Override
    public Trainee create(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Trainee savedTrainee = null;
        try{
            transaction = session.beginTransaction();
            Long traineeId = (Long) session.save(trainee);
            savedTrainee = (Trainee) session.get(Trainee.class, traineeId);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return savedTrainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
//        Long id = trainee.getId();
//        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
//        allTrainee.put(id, trainee);
//        return trainee;
        return null;
    }

    @Override
    public Boolean existById(Long id) {
//        Map<Long, Trainee> allTrainee = dataSource.getTrainees();
//        return allTrainee.containsKey(id);
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
//        Map<Long, Trainee> traineeMap = dataSource.getTrainees();
//        traineeMap.remove(id);
//        return true;
        return null;
    }
}
