package org.example.utils.exception;

public class TrainingTypeNotFoundException extends RuntimeException {
    public TrainingTypeNotFoundException(Long trainingTypeId) {
        super("Training type with id " + trainingTypeId + " not found");
    }
}
