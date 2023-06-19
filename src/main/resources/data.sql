INSERT INTO PERSON (PERSON_ID, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, PHONE_NUMBER)
VALUES
    (1, 'John', 'Doe', 'johndoe@example.com', '1234567890'),
    (2, 'Jane', 'Smith', 'janesmith@example.com', '9876543210'),
    (3, 'Michael', 'Johnson', 'michaeljohnson@example.com', '5555555555'),
    (4, 'Emily', 'Davis', 'emilydavis@example.com', '1111111111'),
    (5, 'Daniel', 'Wilson', 'danielwilson@example.com', '9999999999'),
    (6, 'Olivia', 'Taylor', 'oliviataylor@example.com', '4444444444'),
    (7, 'David', 'Thomas', 'davidthomas@example.com', '7777777777'),
    (8, 'Sophia', 'Anderson', 'sophiaanderson@example.com', '2222222222'),
    (9, 'James', 'Jackson', 'jamesjackson@example.com', '8888888888'),
    (10, 'Ava', 'Harris', 'avaharris@example.com', '3333333333'),
    (11, 'Benjamin', 'Martin', 'benjaminmartin@example.com', '6666666666'),
    (12, 'Mia', 'Thompson', 'miathompson@example.com', '234245345345345');

INSERT INTO LOGIN (USER_ID, USERNAME, USER_PASSWORD)
VALUES
    (1, 'test', 'pass'),
    (2, 'test2', 'pass'),
    (3, 'test3', 'pass');

INSERT INTO TASK (TASK_ID, PERSON_TASK_ID, DESCRIPTION)
VALUES
    (1, 1, 'Complete project deadline'),
    (2, 2, 'Prepare presentation for client meeting'),
    (3, 3, 'Review code and fix bugs'),
    (4, 4, 'Create new user interface design'),
    (5, 5, 'Test application functionality'),
    (6, 6, 'Update database schema'),
    (7, 7, 'Deploy application to production server'),
    (8, 8, 'Optimize performance of the system'),
    (9, 9, 'Implement new feature'),
    (10, 10, 'Write documentation for the project'),
    (11, 11, 'Collaborate with team members on a task'),
    (12, 12, 'Perform system maintenance');