package org.example.dao.impl;


import org.example.dao.AbstractProfileDao;
import org.example.entities.Trainee;
import org.example.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDAO extends AbstractProfileDao<Trainee> {

    @Autowired
    public TraineeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
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
    public Boolean existById(Long id) {
        Session session = sessionFactory.openSession();
        Trainee trainee = session.get(Trainee.class, id);
        session.close();
        return trainee != null;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        return findByUsername(username, Trainee.class);
    }

    @Override
    public Optional<Trainee> findByUsernameAndPassword(String username, String password) {
        return findByUsernameAndPassword(username, password, Trainee.class);
    }

    @Override
    public Optional<Trainee> changePassword(String username, String newPassword) {
        Optional<Trainee> optionalTrainee = findByUsername(username);

        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    User user = trainee.getUser();
                    if (user != null) {
                        user.setPassword(newPassword);
                    }

                    session.merge(trainee);
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
            return Optional.of(trainee);
        } else {
            return Optional.empty();
        }
    }

}
