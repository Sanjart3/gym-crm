package org.example.dao.impl;


import org.example.dao.ProfileDao;
import org.example.entities.Trainee;
import org.example.entities.User;
import org.example.utils.exception.TraineeNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TraineeDAO implements ProfileDao<Trainee> {

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
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            trainee = (Trainee) session.merge(trainee);
            session.update(trainee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
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
    public Trainee findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> trainerRoot = criteriaQuery.from(Trainee.class);
        Join<Trainee, User> trainerUserJoin = trainerRoot.join("user");

        criteriaQuery.where(criteriaBuilder.equal(trainerUserJoin.get("username"), username));
        Session session = sessionFactory.openSession();
        Trainee trainee = session.createQuery(criteriaQuery).uniqueResult();
        session.close();
        return trainee;
    }

    @Override
    public Boolean deleteByUsername(String username) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
        Root<Trainee> trainerRoot = criteriaQuery.from(Trainee.class);
        Join<Trainee, User> trainerUserJoin = trainerRoot.join("user");
        // Build the query to find the trainer by username
        criteriaQuery.where(criteriaBuilder.equal(trainerUserJoin.get("username"), username));

        Trainee trainerToDelete;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Trainee> query = session.createQuery(criteriaQuery);
            trainerToDelete = query.getSingleResult();

            if (trainerToDelete != null) {
                session.delete(trainerToDelete);
                transaction.commit();
                return true;
            }
        } catch (NoResultException e) {
            if (transaction != null) transaction.rollback();
            throw new TraineeNotFoundException(username);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return false;
    }

}
