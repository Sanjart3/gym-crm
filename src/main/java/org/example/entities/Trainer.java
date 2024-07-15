package org.example.entities;

public class Trainer extends User{

    private Long specialization; //TrainingType id
    private Long userId;

    public Trainer(Long specialization, Long userId) {
        this.specialization = specialization;
        this.userId = userId;
    }

    public Long getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Long specialization) {
        this.specialization = specialization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization=" + specialization +
                ", userId=" + userId +
                '}';
    }
}
