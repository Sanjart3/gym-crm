package org.example.services;

import org.example.entities.Trainee;
import org.example.entities.Trainer;

import java.util.List;

public interface TraineeService {
    Trainee findByUsername(String username);
    void passwordChange(String username, String newPassword);
    void changeStatus(String username, boolean status);
    List<Trainee> findAll();
    Trainee findById(Long id);
    Trainee save(Trainee trainee);
    Trainee update(Trainee trainee);
    Boolean deleteByUsername(String username);

    Trainee addTrainer(Long traineeId, Trainer trainer);
    Trainee removeTrainer(Long traineeId, Trainer trainer);

    List<Trainer> findUnassignedTrainers(String username);
}
