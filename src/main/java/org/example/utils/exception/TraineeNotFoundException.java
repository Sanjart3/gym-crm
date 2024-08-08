package org.example.utils.exception;

public class TraineeNotFoundException extends RuntimeException {
    public TraineeNotFoundException(Long traineeId) {
        super("Trainee with id " + traineeId + " not found");
    }
    public TraineeNotFoundException(String username) {
        super("Trainee with id " + username + " not found");
    }
}
