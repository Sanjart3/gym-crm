package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.*;
import org.example.services.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class Facade {
    private final UserService userService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final static Logger logger = LogManager.getLogger(Facade.class);

    public Facade(UserService userService, TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
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
        logger.info("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|");
        logger.info(userService.findAll().toString());
    }

}
