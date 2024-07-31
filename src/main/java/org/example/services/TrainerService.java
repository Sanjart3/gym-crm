package org.example.services;

import org.example.entities.Trainer;

import java.util.List;

public interface TrainerService {
    public List<Trainer> findAll();
    public Trainer findById(Long id);
    public Trainer save(Trainer trainer);
    public Trainer update(Trainer trainer);
}
