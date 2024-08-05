package org.example.utils;

import org.example.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DataSource {
    private static Map<Long, User> users = new TreeMap<>();
    private static Map<Long, Trainee> trainees = new TreeMap<>();
    private static Map<Long, Trainer> trainers = new TreeMap<>();
    private static Map<Long, Training> trainings = new TreeMap<>();
    private static Map<Long, TrainingType> trainingTypes = new TreeMap<>();
    private static final String USER_FILE = "src/main/resources/User.txt";
    private static final String TRAINING_TYPES_FILE = "src/main/resources/TrainingType.txt";
    private static final String TRAINERS_FILE = "src/main/resources/Trainer.txt";
    private static final String TRAINEE_FILE = "src/main/resources/Trainee.txt";
    private static final String TRAININGS_FILE = "src/main/resources/Training.txt";

    private static PasswordGenerator passwordGenerator;
    private static UserNameGenerator userNameGenerator;
    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
    @Autowired
    public void setUserNameGenerator(UserNameGenerator userNameGenerator) {
        this.userNameGenerator = userNameGenerator;
    }

    @PostConstruct
    public void init() {
        initUser();
        initTrainingType();
        initTrainee();
        initTrainer();
        initTraining();
    }

    private static void initUser() {
        try {
            File file = new File(USER_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] entity = line.split(":");
                Long id = Long.parseLong(entity[0]);
                String firstName = entity[1];
                String lastName = entity[2];
                String username = userNameGenerator.generate(firstName, lastName);
                String password = passwordGenerator.generatePassword();
                Boolean isActive = entity[3].toLowerCase().equals("true");
                User user = new User(id, firstName, lastName, username, password, isActive);
                users.put(id, user);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void initTrainingType(){
        try {
            File file = new File(TRAINING_TYPES_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line=reader.readLine())!=null){
                String[] entity = line.split(":");
                Long id = Long.parseLong(entity[0]);
                String name = entity[1];
                TrainingType trainingType = new TrainingType(id, name);
                trainingTypes.put(id, trainingType);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException ie){
            ie.printStackTrace();
        }
    }

    private static void initTrainee() {
//        try {
//            File file = new File(TRAINEE_FILE);
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = reader.readLine())!=null){
//                String[] entity = line.split(":");
//                Long id = Long.parseLong(entity[0]);
//                Long userId = Long.parseLong(entity[1]);
//                String[] dobEntity = entity[2].split("-");
//                LocalDate dob = LocalDate.of(Integer.parseInt(dobEntity[0]), Integer.parseInt(dobEntity[1]), Integer.parseInt(dobEntity[2]));
//                String address = entity[3];
//                Trainee trainee = new Trainee(id, dob, address, userId);
//                trainees.put(id, trainee);
//            }
//        } catch (FileNotFoundException fe){
//            fe.printStackTrace();
//        } catch (IOException ie){
//            ie.printStackTrace();
//        }
    }

    private static void initTrainer() {
//        try {
//            File file = new File(TRAINERS_FILE);
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = reader.readLine())!=null){
//                String[] entity = line.split(":");
//                Long id = Long.parseLong(entity[0]);
//                Long userId = Long.parseLong(entity[1]);
//                Long specialization = Long.parseLong(entity[2]);
//                Trainer trainer = new Trainer(id, userId, specialization);
//                trainers.put(id, trainer);
//            }
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException ie){
//            ie.printStackTrace();
//        }
    }

    private static void initTraining() {
//        try {
//            File file = new File(TRAININGS_FILE);
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = reader.readLine())!=null){
//                String[] entity = line.split(":");
//                Long id = Long.parseLong(entity[0]);
//                Long traineeId = Long.parseLong(entity[1]);
//                Long trainerId = Long.parseLong(entity[2]);
//                String trainingName = entity[3];
//                Long trainingType = Long.parseLong(entity[4]);
//                String[] trainingDateVals = entity[5].split("-");
//                LocalDate trainingDate = LocalDate.of(Integer.parseInt(trainingDateVals[0]), Integer.parseInt(trainingDateVals[1]), Integer.parseInt(trainingDateVals[2]));
//                Integer trainingPeriod = Integer.valueOf(entity[6]);
//                Training training = new Training(id, traineeId, trainerId, trainingName, trainingType, trainingDate, trainingPeriod);
//                trainings.put(id, training);
//            }
//        } catch (FileNotFoundException e){
//            e.printStackTrace();
//        } catch (IOException ie){
//            ie.printStackTrace();
//        }
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }

    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }

    public Map<Long, Training> getTrainings() {
        return trainings;
    }

    public Map<Long, TrainingType> getTrainingTypes() {
        return trainingTypes;
    }
}
