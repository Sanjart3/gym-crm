package org.example.dao;

import org.example.entities.Trainee;
import org.example.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Optional;

public abstract class AbstractProfileDao<T> implements CRDao<T> {
    private final static String USER_ATTRIBUTE = "user";
    private final static String USERNAME_ATTRIBUTE = "username";
    private final static String PASSWORD_ATTRIBUTE = "password";
    protected SessionFactory sessionFactory;

    public AbstractProfileDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public abstract Optional<T> findByUsername(String username);
    public abstract Optional<T> findByUsernameAndPassword(String username, String password);

    public Optional<T> create(T entity){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        T createdEntity = null;
        try{
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Optional.ofNullable(createdEntity);
    }

    public Optional<T> findByUsername(String username, Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        Join<T, User> userJoin = root.join(USER_ATTRIBUTE);

        criteriaQuery.where(criteriaBuilder.equal(userJoin.get(USERNAME_ATTRIBUTE), username));

        Session session = sessionFactory.openSession();
        T entity = session.createQuery(criteriaQuery).uniqueResult();
        session.close();

        return Optional.ofNullable(entity);
    }

    public Optional<T> findByUsernameAndPassword(String username, String password, Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        Join<T, User> userJoin = root.join(USER_ATTRIBUTE);

        criteriaQuery.where(criteriaBuilder.equal(userJoin.get(USERNAME_ATTRIBUTE), username), criteriaBuilder.equal(userJoin.get(PASSWORD_ATTRIBUTE), password));

        Session session = sessionFactory.openSession();
        T entity = session.createQuery(criteriaQuery).uniqueResult();
        session.close();

        return Optional.ofNullable(entity);
    }

    public Optional<T> update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            entity = (T) session.merge(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Optional.ofNullable(entity);
    }

    public abstract Optional<T> changePassword(String username, String newPassword);
}
