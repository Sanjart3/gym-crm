package org.example.services;

import org.example.entities.Trainer;

import java.util.List;

public interface TrainerService {
    public Trainer findByUsername(String username);
    public void changePassword(String username, String newPassword);
    public void changeStatus(String username, boolean status);
    public List<Trainer> findAll();
    public Trainer findById(Long id);
    public Trainer save(Trainer trainer);
    public Trainer update(Trainer trainer);
}
