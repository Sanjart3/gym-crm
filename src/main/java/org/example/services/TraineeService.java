package org.example.services;

import org.example.dto.AuthDto;
import org.example.dto.PasswordChangeDto;
import org.example.entities.Trainee;
import org.example.entities.Trainer;

import java.util.List;

public interface TraineeService {
    Trainee findByUsername(AuthDto auth, String username);
    void changePassword(AuthDto auth, PasswordChangeDto passwordChangeDto);

    void changeStatus(AuthDto auth, String username, boolean status);
    List<Trainee> findAll();
    Trainee findById(Long id);
    AuthDto save(Trainee trainee);
    Trainee update(AuthDto auth, Trainee trainee);
    Boolean deleteByUsername(AuthDto auth, String username);

    Trainee addTrainer(AuthDto auth, Long traineeId, Trainer trainer);
    Trainee removeTrainer(AuthDto auth, Long traineeId, Trainer trainer);

    List<Trainer> findUnassignedTrainers(AuthDto auth, String username);

    void authenticate(AuthDto auth);
}
