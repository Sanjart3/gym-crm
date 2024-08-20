package org.example.converters;

import org.example.dto.TrainerUpdateRequestDto;
import org.example.entities.Trainer;
import org.example.entities.User;

public class TrainerConverter {
    public Trainer fromTrainerUpdateRequestToTrainer(TrainerUpdateRequestDto trainerUpdateRequestDto) {
        String firstName = trainerUpdateRequestDto.getFirstName();
        String lastName = trainerUpdateRequestDto.getLastName();
        String username = trainerUpdateRequestDto.getUsername();
        Boolean isActive = trainerUpdateRequestDto.getIsActive();
        User user = new User(firstName, lastName, username, isActive);
        return new Trainer(trainerUpdateRequestDto.getSpecializationId(), user);
    }
}
