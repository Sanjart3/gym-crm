package org.example.converters;

import org.example.dto.TrainingCreateRequest;
import org.example.entities.Training;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainingConverter {
    public Training fromTrainingCreateRequestToTraining(TrainingCreateRequest trainingCreateRequest) {
        Training training = new Training();
        training.setTrainingName(trainingCreateRequest.getTrainingName());
        training.setTrainingDate(trainingCreateRequest.getTrainingDate());
        training.setTrainingDuration(trainingCreateRequest.getTrainingDuration());
        return training;
    }
}
