package org.example.entities;

import java.time.LocalDate;

public class Trainee extends User{

    private LocalDate dateOfBirth;
    private String address;
    private Long userId;

    public Trainee(LocalDate dateOfBirth, String address, Long userId, String firstName, String lastName , String username, String password, Boolean isActive) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUsername(username);
        this.setActive(isActive);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
