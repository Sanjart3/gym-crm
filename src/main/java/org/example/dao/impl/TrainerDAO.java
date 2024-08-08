package org.example.dao.impl;

import org.example.dao.ProfileDao;
import org.example.entities.Trainer;
import org.example.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.*;

//TODO select by username
//TODO authentication
//TODO criteria
@Repository
public class TrainerDAO implements ProfileDao<Trainer> {

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
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            trainer = (Trainer) session.merge(trainer);
            session.update(trainer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return trainer;
    }

    @Override
    public Boolean existById(Long id) {
        Session session = sessionFactory.openSession();
        Trainer trainer = session.get(Trainer.class, id);
        session.close();
        return trainer != null;
    }

    @Override
    public Trainer findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = criteriaQuery.from(Trainer.class);
        Join<Trainer, User> userJoin = trainerRoot.join("trainer");

        criteriaQuery.where(criteriaBuilder.equal(userJoin.get("username"), username));
        Trainer trainer = sessionFactory.openSession().createQuery(criteriaQuery).uniqueResult();
        return trainer;
    }

    @Override
    public Boolean deleteByUsername(String username) {
        return null;
    }


}
