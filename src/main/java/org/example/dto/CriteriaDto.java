package org.example.dto;

import org.example.entities.TrainingType;

import java.time.LocalDate;

public class CriteriaDto {
    private String traineeUsername;
    private String trainerUsername;
    private LocalDate fromDate;
    private LocalDate toDate;
    TrainingType trainingType;

    public CriteriaDto(String traineeUsername, String trainerUsername, LocalDate fromDate, LocalDate toDate, TrainingType trainingType) {
        this.traineeUsername = traineeUsername;
        this.trainerUsername = trainerUsername;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.trainingType = trainingType;
    }

    public String getTraineeUsername() {
        return traineeUsername;
    }

    public void setTraineeUsername(String traineeUsername) {
        this.traineeUsername = traineeUsername;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }
}
