-- Inserting user records
INSERT INTO users (id, first_name, last_name, username, password, is_active) VALUES
                                                                             (1, 'John', 'Doe', 'john.doe', 'password1', true),
                                                                             (2, 'Jane', 'Doe', 'jane.doe', 'password2', true),
                                                                             (3, 'Alice', 'Smith', 'alice.smith', 'password3', true),
                                                                             (4, 'Bob', 'Johnson', 'bob.johnson', 'password4', true),
                                                                             (5, 'Eva', 'Williams', 'eva.williams', 'password5', true),
                                                                             (6, 'Michael', 'Brown', 'michael.brown', 'password6', true),
                                                                             (7, 'Olivia', 'Davis', 'olivia.davis', 'password7', true),
                                                                             (8, 'Daniel', 'Miller', 'daniel.miller', 'password8', true),
                                                                             (9, 'Sophia', 'Wilson', 'sophia.wilson', 'password9', true),
                                                                             (10, 'Matthew', 'Moore', 'matthew.moore', 'password10', true);

-- Inserting training types
INSERT INTO training_type(id, name) VALUES
                                    (1, 'Boxing'),
                                    (2, 'Karate'),
                                    (3, 'Bodybuilding'),
                                    (4, 'Strict Diet'),
                                    (5, 'Kickboxing'),
                                    (6, 'Powerlifting'),
                                    (7, 'Running'),
                                    (8, 'Weight Loss'),
                                    (9, 'Postural and technique corrections'),
                                    (10, 'High-intensity interval training');

-- Inserting trainee records with user_id references
INSERT INTO trainees (id, date_of_birth, address, user_id) VALUES
                                                           (1, '1990-01-01', 'Tashkent, Mukimi 1', 1), -- John Doe
                                                           (2, '1991-02-02', 'Tashkent Mukimi 2', 2), -- Jane Doe
                                                           (3, '1992-03-03', 'Tashkent Mukimi 3', 3), -- Alice Smith
                                                           (4, '1993-04-04', 'Tashkent Mukimi 4', 4), -- Bob Johnson
                                                           (5, '1994-05-05', 'Tashkent Mukimi 5', 5), -- Eva Williams
                                                           (6, '1995-06-06', 'Tashkent Mukimi 6', 6), -- Michael Brown
                                                           (7, '1996-07-07', 'Tashkent Mukimi 7', 7); -- Olivia Davis

-- Inserting trainer records with specialization (TrainingType ID) and user_id references
INSERT INTO trainers (id, specialization, user_id) VALUES
                                                   (1, 1, 8), -- Daniel Miller (Boxing)
                                                   (2, 3, 9), -- Sophia Wilson (Bodybuilding)
                                                   (3, 5, 10); -- Matthew Moore (Kickboxing)

-- Inserting training sessions with trainee_id, trainer_id, training_type_id references
INSERT INTO trainings (id, training_duration, trainee_id, trainer_id, training_date, training_type_id, training_name) VALUES
                                                                                                                      (1, 60, 1, 2, '2023-01-18', 8, 'Strength training session with trainee: John Doe and trainer: Sophia Wilson'),
                                                                                                                      (2, 60, 1, 3, '2023-01-25', 9, 'Boxing technique session with trainee: John Doe and trainer: Daniel Miller'),
                                                                                                                      (3, 60, 2, 2, '2023-01-12', 8, 'Nutrition consultation with trainee: Jane Doe and trainer: Sophia Wilson'),
                                                                                                                      (4, 60, 2, 1, '2023-01-19', 8, 'Karate training session with trainee: Jane Doe and trainer: Daniel Miller'),
                                                                                                                      (5, 60, 3, 2, '2023-01-13', 8, 'Bodybuilding training session with trainee: Alice Smith and trainer: Sophia Wilson'),
                                                                                                                      (6, 60, 3, 3, '2023-01-20', 9, 'Powerlifting technique session with trainee: Alice Smith and trainer: Daniel Miller'),
                                                                                                                      (7, 60, 4, 2, '2023-01-14', 8, 'Weight loss consultation with trainee: Bob Johnson and trainer: Sophia Wilson'),
                                                                                                                      (8, 60, 4, 3, '2023-01-21', 9, 'Kickboxing training session with trainee: Bob Johnson and trainer: Daniel Miller'),
                                                                                                                      (9, 60, 5, 1, '2023-01-15', 8, 'Strict diet consultation with trainee: Eva Williams and trainer: Daniel Miller'),
                                                                                                                      (10, 60, 5, 2, '2023-01-22', 8, 'Running training session with trainee: Eva Williams and trainer: Sophia Wilson'),
                                                                                                                      (11, 60, 6, 3, '2023-01-16', 9, 'Weight loss coaching session with trainee: Michael Brown and trainer: Daniel Miller'),
                                                                                                                      (12, 60, 6, 2, '2023-01-23', 8, 'Postural and technique corrections session with trainee: Michael Brown and trainer: Sophia Wilson'),
                                                                                                                      (13, 60, 7, 3, '2023-01-17', 9, 'High-intensity interval training session with trainee: Olivia Davis and trainer: Daniel Miller'),
                                                                                                                      (14, 60, 7, 1, '2023-01-24', 8, 'Nutrition consultation with trainee: Olivia Davis and trainer: Daniel Miller');
