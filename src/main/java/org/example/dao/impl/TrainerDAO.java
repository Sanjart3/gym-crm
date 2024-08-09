package org.example.dao.impl;

import org.example.dao.AbstractProfileDao;
import org.example.entities.Trainer;
import org.example.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDAO extends AbstractProfileDao<Trainer> {

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        return findByUsername(username, Trainer.class);
    }

    @Override
    public Optional<Trainer> findByUsernameAndPassword(String username, String password) {
        return findByUsernameAndPassword(username, password, Trainer.class);
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
    public Boolean existById(Long id) {
        Session session = sessionFactory.openSession();
        Trainer trainer = session.get(Trainer.class, id);
        session.close();
        return trainer != null;
    }

    @Override
    public Optional<Trainer> changePassword(String username, String newPassword) {
        Optional<Trainer> optionalTrainee = findByUsername(username);

        if (optionalTrainee.isPresent()) {
            Trainer trainer = optionalTrainee.get();
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    User user = trainer.getUser();
                    if (user != null) {
                        user.setPassword(newPassword);
                    }

                    session.merge(trainer);
                    transaction.commit();

                } catch (HibernateException e) {
                    if (transaction != null) transaction.rollback();
                    e.printStackTrace();
                    return Optional.empty();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return Optional.empty();
            }
            return Optional.of(trainer);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean deleteByUsername(String username) {
        return null;
    }
}
