package org.example;

import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.entities.User;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TraineeTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Trainee> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Trainee> criteriaQuery;

    @Mock
    private Root<Trainee> root;

    @SuppressWarnings("unchecked")
    @Mock
    private Join<Trainee, User> userJoin;

    @InjectMocks
    private TraineeDAO traineeDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainee.class)).thenReturn(root);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
//        when(root.join("user")).thenReturn(userJoin);
    }

    @Test
    public void testCreateTrainee() {
        Trainee trainee = getTrainee();
        when(session.save(any(Trainee.class))).thenReturn(null);

        traineeDAO.create(trainee);

        verify(session).save(trainee);
        verify(session).beginTransaction();
        verify(session.getTransaction()).commit();
        verify(session).close();
    }

    @Test
    public void testFindByUsername() {
        Trainee trainee = new Trainee();
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);

        Optional<Trainee> result = traineeDAO.findByUsername("testUsername");

        assertTrue(result.isPresent());
        verify(session).createQuery(anyString(), eq(Trainee.class));
        verify(session).close();
    }

    @Test
    public void testReadAll() {
        List<Trainee> traineeList = new ArrayList<>();
        when(session.createQuery("from Trainee", Trainee.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(traineeList);

        List<Trainee> result = traineeDAO.readAll();

        assertEquals(traineeList, result);
        verify(session).createQuery("from Trainee", Trainee.class);
        verify(session).close();
    }

    @Test
    public void testReadById() {
        Trainee trainee = new Trainee();
        when(session.get(Trainee.class, 1L)).thenReturn(trainee);

        Trainee result = traineeDAO.readById(1L);

        assertEquals(trainee, result);
        verify(session).get(Trainee.class, 1L);
        verify(session).close();
    }

    @Test
    public void testExistById() {
        when(session.get(Trainee.class, 1L)).thenReturn(new Trainee());

        Boolean result = traineeDAO.existById(1L);

        assertTrue(result);
        verify(session).get(Trainee.class, 1L);
        verify(session).close();
    }

    @Test
    public void testChangePassword() {
        Trainee trainee = new Trainee();
        trainee.setUser(new User());
        trainee.getUser().setPassword("oldPassword");

        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);

        traineeDAO.changePassword("newPassword", "testUsername", Trainee.class);

        assertEquals("newPassword", trainee.getUser().getPassword());
        verify(session).createQuery(anyString(), eq(Trainee.class));
        verify(session).close();
    }

    public Trainee getTrainee(){
        User user = new User("Sanjar", "Totliboyev", true);
        Trainee trainee = new Trainee(LocalDate.of(2003, 06, 04), "Navoiy Galaba shokh 3", user);
        return trainee;
    }
}
