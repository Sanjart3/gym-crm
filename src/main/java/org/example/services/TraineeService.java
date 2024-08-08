package org.example.services;

import org.example.entities.Trainee;

import java.util.List;

public interface TraineeService {
    List<Trainee> findAll();
    Trainee findById(Long id);
    Trainee save(Trainee trainee);
    Trainee update(Trainee trainee);
    Boolean delete(String username);
}
