package org.example.dao.impl;

import org.example.dao.BaseDao;
import org.example.entities.User;
import org.example.utils.PasswordGenerator;
import org.example.utils.UserNameGenerator;
import org.example.utils.exception.UserNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class UserDAO implements BaseDao<User> {

    private static SessionFactory sessionFactory;
    private PasswordGenerator passwordGenerator;
    private UserNameGenerator userNameGenerator;
    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
    @Autowired
    public void setUserNameGenerator(UserNameGenerator userNameGenerator) {
        this.userNameGenerator = userNameGenerator;
    }
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> readAll() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("from User").list();
        session.close();
        return users;
    }

    @Override
    public User readById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public User create(User user) {
        String username = userNameGenerator.generate(user.getFirstName(), user.getLastName());
        String password = passwordGenerator.generatePassword();
        user.setUsername(username);
        user.setPassword(password);

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        User savedUser = null;
        try {
            transaction = session.beginTransaction();
            Long userId = (Long) session.save(user); // saving and getting userId
            savedUser = session.get(User.class, userId); // getting user using userId
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return savedUser;
    }


    @Override
    public User update(User user) {
//        Long id = user.getId();
//        Map<Long, User> userMap = dataSource.getUsers();
//        userMap.put(id, user);
//        return user;
        return null;
    }

    @Override
    public Boolean existById(Long id) {
//        Map<Long, User> userMap = dataSource.getUsers();
//        return userMap.containsKey(id);
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        if (user != null) {
            Transaction transaction = null;
            boolean deleted = false;
            try{
                transaction = session.beginTransaction();
                session.delete(user);
                transaction.commit();
                deleted = true;
            } catch (HibernateException e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
            return deleted;
        }
        throw new UserNotFoundException(id);
    }
}
