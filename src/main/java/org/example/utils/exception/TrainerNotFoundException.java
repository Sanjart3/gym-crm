package org.example.utils.exception;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(Long trainerId) {
        super("Trainer with id " + trainerId + " not found");
    }
    public TrainerNotFoundException(String username) {
        super("Trainer with username " + username + " not found");
    }
}
