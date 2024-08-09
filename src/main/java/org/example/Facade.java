package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.*;
import org.example.services.impl.TraineeServiceImpl;
import org.example.services.impl.TrainerServiceImpl;
import org.example.services.impl.TrainingServiceImpl;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class Facade {
    private final UserServiceImpl userService;
    private final TraineeServiceImpl traineeService;
    private final TrainerServiceImpl trainerService;
    private final TrainingServiceImpl trainingService;

    private final static Logger logger = LogManager.getLogger(Facade.class);

    public Facade(UserServiceImpl userService, TraineeServiceImpl traineeService, TrainerServiceImpl trainerService, TrainingServiceImpl trainingService) {
        this.userService = userService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public void readAllUsers(){
        List<User> users = userService.findAll();
        logger.info("--------------------------------------------");
        logger.info("These are pre-constructed Users:");
        for (User user : users) {
            logger.info(user.toString());
        }
        logger.info("--------------------------------------------");
    }

    public void readAllTrainees(){
        List<Trainee> trainees = traineeService.findAll();
        logger.info("These are pre-constructed Trainees:");
        for (Trainee traine : trainees) {
            logger.info(traine.toString());
        }
        logger.info("--------------------------------------------");
    }

    public void readAllTrainers(){
        List<Trainer> trainers = trainerService.findAll();
        logger.info("These are pre-constructed Trainers:");
        for (Trainer trainer : trainers) {
            logger.info(trainer.toString());
        }
        logger.info("--------------------------------------------");
    }

    public void readAllTrainings(){
        List<Training> trainings = trainingService.findAll();
        logger.info("These are pre-constructed Trainings:");
        for (Training training : trainings) {
            logger.info(training.toString());
        }
        logger.info("---------------------------------------------");
    }

    public void createUser(){
        User user = new User("Sanjar", "Totliboyev", true);
        userService.save(user);
        User secondUser = new User("Ali", "Aliyev", true);
        userService.save(secondUser);
    }

    public void createTrainee(){
        User user = new User("Sanjar", "Totliboyev", true);
        Trainee trainee = new Trainee(LocalDate.of(2004,3,9), "Samarkhand", user);
        traineeService.save(trainee);
    }

    public void createTrainer(){
        User user = new User("Shohjahon", "Totliboyev", true);
        Trainer trainer = new Trainer(2l, user);
        trainerService.save(trainer);
    }

//    public void createTraining(){
//        Training training = new Training(7l, 8l, "Kuchamala", 2l, LocalDate.of(2024, 9, 01), 60);
//        trainingService.save(training);
//    }

}
