package org.example.utils.exception;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(Long trainerId) {
        super("Trainer with id " + trainerId + " not found");
    }
}
