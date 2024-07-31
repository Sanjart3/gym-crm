package org.example;

import org.example.dao.impl.TrainerDAO;
import org.example.entities.Trainer;
import org.example.services.impl.TrainerService;
import org.example.utils.validation.impl.TrainerValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TrainerTest {

    @Mock
    private TrainerDAO trainerDAO;
    @InjectMocks
    private TrainerService trainerService;
    @Mock
    private TrainerValidation trainerValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Trainer> trainerList = new ArrayList<>();
        when(trainerDAO.readAll()).thenReturn(trainerList);
        List<Trainer> trainers = trainerService.findAll();
        verify(trainerDAO, times(1)).readAll();
        assertEquals(trainerList, trainers);
    }

    @Test
    public void testFindById() {
        Trainer trainer = new Trainer();
        when(trainerDAO.readById(1L)).thenReturn(trainer);
        Trainer foundTrainer = trainerService.findById(1L);
        verify(trainerDAO, times(1)).readById(1L);
        assertEquals(trainer, foundTrainer);
    }

    @Test
    public void testSave() {
        Trainer trainer = new Trainer();
        when(trainerValidation.isValidForCreate(trainer)).thenReturn(true);
        when(trainerDAO.create(trainer)).thenReturn(trainer);
        Trainer savedTrainer = trainerService.save(trainer);
        verify(trainerDAO, times(1)).create(trainer);
        assertEquals(trainer, savedTrainer);
    }

    @Test
    public void testUpdate() {
        Trainer trainer = new Trainer();
        when(trainerValidation.isValidForUpdate(trainer)).thenReturn(true);
        when(trainerDAO.update(trainer)).thenReturn(trainer);
        Trainer updatedTrainer = trainerService.update(trainer);
        verify(trainerDAO, times(1)).update(trainer);
        assertEquals(trainer, updatedTrainer);
    }
}
