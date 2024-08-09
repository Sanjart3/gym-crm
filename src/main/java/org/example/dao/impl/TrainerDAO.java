package org.example.dao.impl;

import org.example.dao.AbstractProfileDao;
import org.example.entities.Trainer;
import org.example.utils.PasswordGenerator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TrainerDAO extends AbstractProfileDao<Trainer> {

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
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
    public Optional<Trainer> create(Trainer trainer) {
        return create(trainer, Trainer.class);
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
        return changePassword(username, newPassword, Trainer.class);
    }

    @Override
    public Boolean deleteByUsername(String username) {
        return null;
    }

    @Override
    public Boolean changeStatus(String username, Boolean status) {
        return changeStatus(username, status, Trainer.class);
    }
}
