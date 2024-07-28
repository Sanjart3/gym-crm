package org.example.dto;

public class TrainerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private Long trainingTypeId;

    public TrainerRequest(Long id, String firstName, String lastName, Boolean isActive, Long trainingTypeId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.trainingTypeId = trainingTypeId;
    }

    public TrainerRequest(String firstName, String lastName, Boolean isActive, Long trainingTypeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.trainingTypeId = trainingTypeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }
}
