package org.example.services;

import org.example.dto.AuthDto;
import org.example.entities.Training;

import java.util.List;

public interface TrainingService {
    public List<Training> findAll();
    public Training findById(Long id);
    public Training save(Training training, String traineeUsername, String trainerUsername, AuthDto authDto);
}
