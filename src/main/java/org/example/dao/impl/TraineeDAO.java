package org.example.dao.impl;


import org.example.entities.Trainee;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TraineeDAO {

    private Map<Integer, Trainee> trainees = new HashMap<>();

    @PostConstruct
    public void init() {

    }
}
