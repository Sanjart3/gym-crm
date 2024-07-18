package org.example;

import org.example.dao.impl.TrainingTypeDAO;
import org.example.entities.TrainingType;
import org.example.services.impl.TrainingTypeService;
import org.example.utils.validation.impl.TrainingTypeValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainingTypeTest {

    @Mock
    private TrainingTypeDAO trainingTypeDAO;
    @InjectMocks
    private TrainingTypeService trainingTypeService;
    @Mock
    private TrainingTypeValidation trainingTypeValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<TrainingType> trainingTypeList = new ArrayList<>();
        when(trainingTypeDAO.readAll()).thenReturn(trainingTypeList);

        List<TrainingType> trainingTypes = trainingTypeService.findAll();

        verify(trainingTypeDAO, times(1)).readAll();
        assertEquals(trainingTypeList, trainingTypes);
    }

    @Test
    public void testFindById() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeDAO.readById(1L)).thenReturn(trainingType);

        TrainingType foundTrainingType = trainingTypeService.findById(1L);

        verify(trainingTypeDAO, times(1)).readById(1L);
        assertEquals(trainingType, foundTrainingType);
    }

    @Test
    public void testSave() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeValidation.isValidForCreate(trainingType)).thenReturn(true);
        when(trainingTypeDAO.create(trainingType)).thenReturn(trainingType);

        TrainingType savedTrainingType = trainingTypeService.save(trainingType);

        verify(trainingTypeDAO, times(1)).create(trainingType);
        assertEquals(trainingType, savedTrainingType);
    }

    @Test
    public void testUpdate() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeValidation.isValidForUpdate(trainingType)).thenReturn(true);
        when(trainingTypeDAO.update(trainingType)).thenReturn(trainingType);

        TrainingType updatedTrainingType = trainingTypeService.update(trainingType);

        verify(trainingTypeDAO, times(1)).update(trainingType);
        assertEquals(trainingType, updatedTrainingType);
    }

    @Test
    public void testDelete() {
        when(trainingTypeDAO.deleteById(1L)).thenReturn(true);

        Boolean result = trainingTypeService.delete(1L);

        verify(trainingTypeDAO, times(1)).deleteById(1L);
        assertEquals(true, result);
    }
}
