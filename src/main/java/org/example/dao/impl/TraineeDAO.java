package org.example.dao.impl;


import org.example.dao.AbstractProfileDao;
import org.example.entities.Trainee;
import org.example.entities.Trainer;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDAO extends AbstractProfileDao<Trainee> {

    public static final String USER_ATTRIBUTE = "user";
    public static final String USERNAME_ATTRIBUTE = "username";


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
        return changePassword(username, newPassword, Trainee.class);
    }

    public Boolean deleteByUsername(String username) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = criteriaQuery.from(Trainer.class);
        Join<Trainer, User> trainerUserJoin = trainerRoot.join(USER_ATTRIBUTE);

        criteriaQuery.where(criteriaBuilder.equal(trainerUserJoin.get(USERNAME_ATTRIBUTE), username));

        Trainer trainerToDelete = null;
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Trainer> query = session.createQuery(criteriaQuery);
            trainerToDelete = query.getSingleResult();

            if (trainerToDelete != null) {
                session.delete(trainerToDelete);
                transaction.commit();
                return true; // Successfully deleted
            }
        } catch (NoResultException e) {
            // No Trainer found with the given username
            if (transaction != null) transaction.rollback();
            throw new TraineeNotFoundException(username);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return false; // Deletion failed or Trainer not found
    }

    @Override
    public Boolean changeStatus(String username, Boolean status) {
        return changeStatus(username, status, Trainee.class);
    }

    public Optional<Trainee> addTrainerToTrainee(Long traineeId, Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                trainee.addTrainer(trainer);
                session.merge(trainee);
                transaction.commit();
                return Optional.of(trainee);
            } else {
                transaction.rollback();
                throw new TraineeNotFoundException(traineeId);  // Trainee not found
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Trainee> removeTrainerFromTrainee(Long traineeId, Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                trainee.removeTrainer(trainer);
                session.merge(trainee);
                transaction.commit();
                return Optional.of(trainee);
            } else {
                transaction.rollback();
                throw new TraineeNotFoundException(traineeId);  // Trainee not found
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Trainer> getUnassignedTrainersByTraineeUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Trainee> traineeQuery = criteriaBuilder.createQuery(Trainee.class);
            Root<Trainee> traineeRoot = traineeQuery.from(Trainee.class);
            Join<Trainee, User> userJoin = traineeRoot.join(USER_ATTRIBUTE);
            traineeQuery.where(criteriaBuilder.equal(userJoin.get(USERNAME_ATTRIBUTE), username));

            Trainee trainee = session.createQuery(traineeQuery).uniqueResult();

            if (trainee == null) {
                throw new TraineeNotFoundException(username); // No trainee found with that username
            }

            // Step 2: Get the list of assigned trainers
            List<Trainer> assignedTrainers = trainee.getTrainers();

            // Step 3: Query for all trainers not assigned to this trainee
            CriteriaQuery<Trainer> trainerQuery = criteriaBuilder.createQuery(Trainer.class);
            Root<Trainer> trainerRoot = trainerQuery.from(Trainer.class);

            if (!assignedTrainers.isEmpty()) {
                trainerQuery.where(trainerRoot.in(assignedTrainers).not());
            }

            Query<Trainer> query = session.createQuery(trainerQuery);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();  // Handle error case
        }
    }

}
