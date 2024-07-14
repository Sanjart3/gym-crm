package org.example.entities;

public class Trainer extends User{

    private String specialization;
    private Long userId;

    public Trainer(String specialization, Long userId, String firstName, String lastName, String username, String password, Boolean isActive) {
        this.specialization = specialization;
        this.userId = userId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUsername(username);
        this.setActive(isActive);
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
