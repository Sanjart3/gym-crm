package org.example;

import org.example.dao.impl.TrainingDAO;
import org.example.entities.Training;
import org.example.services.TrainingService;
import org.example.utils.validation.impl.TrainingValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainingTest {
    @Mock
    private TrainingDAO trainingDAO;
    @InjectMocks
    private TrainingService trainingService;
    @Mock
    private TrainingValidation trainingValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Training> trainingList = new ArrayList<>();
        when(trainingDAO.readAll()).thenReturn(trainingList);
        List<Training> trainings = trainingService.findAll();
        verify(trainingDAO, times(1)).readAll();
        assertEquals(trainingList, trainings);
    }

    @Test
    public void testFindById() {
        Training training = new Training();
        when(trainingDAO.readById(1L)).thenReturn(training);
        Training foundTraining = trainingService.findById(1L);
        verify(trainingDAO, times(1)).readById(1L);
        assertEquals(training, foundTraining);
    }

    @Test
    public void testSave() {
        Training training = new Training();
        when(trainingValidation.isValidForCreate(training)).thenReturn(true);
        when(trainingDAO.create(training)).thenReturn(training);
        Training savedTraining = trainingService.save(training);
        verify(trainingDAO, times(1)).create(training);
        assertEquals(training, savedTraining);
    }

    @Test
    public void testUpdate() {
        Training training = new Training();
        when(trainingValidation.isValidForUpdate(training)).thenReturn(true);
        when(trainingDAO.update(training)).thenReturn(training);
        Training updatedTraining = trainingService.update(training);
        verify(trainingDAO, times(1)).update(training);
        assertEquals(training, updatedTraining);
    }

    @Test
    public void testDelete() {
        when(trainingDAO.deleteById(1L)).thenReturn(true);
        Boolean result = trainingService.delete(1L);
        verify(trainingDAO, times(1)).deleteById(1L);
        assertEquals(true, result);
    }
}