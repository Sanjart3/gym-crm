package org.example;

import org.example.config.MyAppConfig;
import org.example.dao.impl.TraineeDAO;
import org.example.entities.Trainee;
import org.example.services.impl.TraineeService;
import org.example.utils.validation.impl.TraineeValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyAppConfig.class)
public class TraineeTest {

    @Mock
    private TraineeDAO traineeDAO;
    @InjectMocks
    private TraineeService traineeService;
    @Mock
    private TraineeValidation traineeValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Trainee> traineeList = new ArrayList<>();
        when(traineeDAO.readAll()).thenReturn(traineeList);

        List<Trainee> trainees = traineeService.findAll();

        verify(traineeDAO, times(1)).readAll();
        assertEquals(traineeList, trainees);
    }

    @Test
    public void testFindById() {
        Trainee trainee = null;
        when(traineeDAO.readById(1L)).thenReturn(trainee);

        Trainee foundTrainee = traineeService.findById(1L);

        verify(traineeDAO, times(1)).readById(1L);
        assertEquals(trainee, foundTrainee);
    }

    @Test
    public void testSave() {
        Trainee trainee = null;
        when(traineeValidation.isValidForCreate(trainee)).thenReturn(true);
        when(traineeDAO.create(trainee)).thenReturn(trainee);

        Trainee savedTrainee = traineeService.save(trainee);

        verify(traineeDAO, times(1)).create(trainee);
        assertEquals(trainee, savedTrainee);
    }

    @Test
    public void testUpdate() {
        Trainee trainee = null;
        when(traineeValidation.isValidForUpdate(trainee)).thenReturn(true);
        when(traineeDAO.update(trainee)).thenReturn(trainee);

        Trainee updatedTrainee = traineeService.update(trainee);

        verify(traineeDAO, times(1)).update(trainee);
        assertEquals(trainee, updatedTrainee);
    }

    @Test
    public void testDelete() {
        when(traineeDAO.deleteById(1L)).thenReturn(true);

        Boolean result = traineeService.delete(1L);

        verify(traineeDAO, times(1)).deleteById(1L);
        assertEquals(true, result);
    }
}
