package org.example.services;

import org.example.entities.Training;

import java.util.List;

public interface TrainingService {
    public List<Training> findAll();
    public Training findById(Long id);
    public Training save(Training training);
}
