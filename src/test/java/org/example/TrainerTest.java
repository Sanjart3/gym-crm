package org.example;

import org.example.dao.impl.TrainerDAO;
import org.example.entities.Trainer;
import org.example.entities.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrainerTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Trainer> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Trainer> criteriaQuery;
    @Mock
    private Criteria criteria;

    @Mock
    private Root<Trainer> root;
    @Mock
    private Join<Trainer, User> userJoin;

    @InjectMocks
    private TrainerDAO trainerDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(sessionFactory.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainer.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainer.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(session.createCriteria(Trainer.class)).thenReturn(criteria);
//        when(root.join("user")).thenReturn(userJoin);
    }

    @Test
    public void testCreateTrainer() {
        Trainer trainer = getTrainer();
        when(session.save(any(Trainer.class))).thenReturn(null);

        trainerDAO.create(trainer);

        verify(session).save(trainer);
        verify(session).beginTransaction();
        verify(session.getTransaction()).commit();
        verify(session).close();
    }

    @Test
    public void testFindByUsername() {
        Trainer trainer = new Trainer();
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainer);

        Optional<Trainer> result = trainerDAO.findByUsername("testUsername");

        assertTrue(result.isPresent());
        verify(session).createQuery(anyString(), eq(Trainer.class));
        verify(session).close();
    }

    @Test
    public void testReadAll() {
        List<Trainer> trainerList = new ArrayList<>();
        when(session.createQuery("from Trainer", Trainer.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(trainerList);

        List<Trainer> result = trainerDAO.readAll();

        assertEquals(trainerList, result);
        verify(session).createQuery("from Trainer", Trainer.class);
        verify(session).close();
    }

    @Test
    public void testReadById() {
        Trainer trainer = new Trainer();
        when(session.get(Trainer.class, 1L)).thenReturn(trainer);

        Trainer result = trainerDAO.readById(1L);

        assertEquals(trainer, result);
        verify(session).get(Trainer.class, 1L);
        verify(session).close();
    }

    @Test
    public void testExistById() {
        when(session.get(Trainer.class, 1L)).thenReturn(new Trainer());

        Boolean result = trainerDAO.existById(1L);

        assertTrue(result);
        verify(session).get(Trainer.class, 1L);
        verify(session).close();
    }

    @Test
    public void testChangePassword() {
        Trainer trainer = new Trainer();
        trainer.getUser().setPassword("oldPassword");

        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainer);

        trainerDAO.changePassword("John.Doe", "newPassword", Trainer.class);

        assertEquals("newPassword", trainer.getUser().getPassword());
        verify(session).createQuery(anyString(), eq(Trainer.class));
        verify(session).close();
    }

    public Trainer getTrainer(){
        User user = new User("Shohjahon", "Totliboyev", true);
        Trainer trainer = new Trainer(1l, user);
        return trainer;
    }
}
