package org.example.utils.exception;

public class TrainingNotFoundException extends RuntimeException {
    public TrainingNotFoundException(Long trainingId) {
        super("Training with id " + trainingId + " not found");
    }
}
