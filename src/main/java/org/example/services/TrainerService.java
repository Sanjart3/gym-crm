package org.example.services;

import org.example.dto.AuthDto;
import org.example.entities.Trainer;

import java.util.List;

public interface TrainerService {
    public Trainer findByUsername(AuthDto auth, String username);
    public void changePassword(AuthDto auth, String username, String newPassword);
    public void changeStatus(AuthDto auth, String username, boolean status);
    public List<Trainer> findAll();
    public Trainer findById(Long id);
    public Trainer save(Trainer trainer);
    public Trainer update(AuthDto auth, Trainer trainer);
    public void authenticate(AuthDto auth);
}
