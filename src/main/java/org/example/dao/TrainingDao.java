package org.example.dao;

import org.example.dto.CriteriaDto;
import org.example.entities.Trainee;
import org.example.entities.Trainer;
import org.example.entities.Training;
import org.example.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.*;
import java.util.List;

public abstract class TrainingDao implements CRDao<Training> {
    private SessionFactory sessionFactory;

    public List<Training> searchTrainings(CriteriaDto criteriaDto) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Training> cq = cb.createQuery(Training.class);
            Root<Training> trainingRoot = cq.from(Training.class);

            // Join with Trainee and User to filter by trainee username
            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");
            Join<Trainee, User> traineeUserJoin = traineeJoin.join("user");

            Predicate criteria = cb.equal(traineeUserJoin.get("username"), criteriaDto.getTraineeUsername());

            if (criteriaDto.getFromDate() != null) {
                criteria = cb.and(criteria, cb.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), criteriaDto.getFromDate()));
            }

            if (criteriaDto.getToDate() != null) {
                criteria = cb.and(criteria, cb.lessThanOrEqualTo(trainingRoot.get("trainingDate"), criteriaDto.getToDate()));
            }

            if (criteriaDto.getTraineeUsername() != null && !criteriaDto.getTrainerUsername().isEmpty()) {
                Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");
                Join<Trainer, User> trainerUserJoin = trainerJoin.join("user");
                criteria = cb.and(criteria, cb.like(trainerUserJoin.get("firstName"), "%" + criteriaDto.getTrainerUsername() + "%"));
            }

            if (criteriaDto.getTrainingType() != null) {
                criteria = cb.and(criteria, cb.equal(trainingRoot.get("trainingType"), criteriaDto.getTrainingType()));
            }

            cq.where(criteria);

            return session.createQuery(cq).getResultList();
        }
    }
}
