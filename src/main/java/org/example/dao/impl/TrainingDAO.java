package org.example.dao.impl;

import org.example.entities.Training;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainingDAO {

    private Map<Integer, Training> trainings;

    @PostConstruct
    public void init() {
        trainings = new HashMap<>();

    }
}
