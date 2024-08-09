package org.example.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trainees")
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "address")
    private String address;
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private List<Trainer> trainers;

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void addTrainer(Trainer trainer) {
        if (this.trainers != null && !this.trainers.contains(trainer)) {
            this.trainers.add(trainer);
        }
    }

    public void removeTrainer(Trainer trainer) {
        if (this.trainers != null) {
            this.trainers.remove(trainer);
        }
    }


    public Trainee(Long id, LocalDate dateOfBirth, String address, User user) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.user = user;
    }

    public Trainee(LocalDate dateOfBirth, String address, User user) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.user = user;
    }

    public Trainee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + user +
                '}';
    }
}
