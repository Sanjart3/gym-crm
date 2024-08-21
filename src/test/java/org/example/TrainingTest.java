//package org.example;
//
//import org.example.dao.impl.TrainingDAO;
//import org.example.entities.Training;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class TrainingTest {
//
//    @Mock
//    private SessionFactory sessionFactory;
//
//    @Mock
//    private Session session;
//
//    @Mock
//    private Query<Training> query;
//
//    @Mock
//    private CriteriaBuilder criteriaBuilder;
//
//    @Mock
//    private CriteriaQuery<Training> criteriaQuery;
//
//    @Mock
//    private Root<Training> root;
//
//    @InjectMocks
//    private TrainingDAO trainingDAO;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(sessionFactory.openSession()).thenReturn(session);
//        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
//        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
//        when(criteriaQuery.from(Training.class)).thenReturn(root);
//        when(session.createQuery(criteriaQuery)).thenReturn(query);
//    }
//
//    @Test
//    public void testCreateTraining() {
//        Training training = new Training();
//        when(session.save(any(Training.class))).thenReturn(null);
//
//        trainingDAO.create(training);
//
//        verify(session).save(training);
//        verify(session).beginTransaction();
//        verify(session.getTransaction()).commit();
//        verify(session).close();
//    }
//
//
//    @Test
//    public void testReadAll() {
//        List<Training> trainingList = new ArrayList<>();
//        when(session.createQuery("from Training", Training.class)).thenReturn(query);
//        when(query.getResultList()).thenReturn(trainingList);
//
//        List<Training> result = trainingDAO.readAll();
//
//        assertEquals(trainingList, result);
//        verify(session).createQuery("from Training", Training.class);
//        verify(session).close();
//    }
//
//    @Test
//    public void testReadById() {
//        Training training = new Training();
//        when(session.get(Training.class, 1L)).thenReturn(training);
//
//        Training result = trainingDAO.readById(1L);
//
//        assertEquals(training, result);
//        verify(session).get(Training.class, 1L);
//        verify(session).close();
//    }
//
//    @Test
//    public void testExistById() {
//        when(session.get(Training.class, 1L)).thenReturn(new Training());
//
//        Boolean result = trainingDAO.existById(1L);
//
//        assertTrue(result);
//        verify(session).get(Training.class, 1L);
//        verify(session).close();
//    }
//}
