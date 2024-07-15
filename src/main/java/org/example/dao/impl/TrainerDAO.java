package org.example.dao.impl;

import org.example.entities.Trainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainerDAO {
    private Map<Long, Trainer> trainers = new HashMap<Long, Trainer>();


    @PostConstruct
    public void init(){

    }

}
