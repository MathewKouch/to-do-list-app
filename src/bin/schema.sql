CREATE TABLE PERSON (
                        PERSON_ID SERIAL PRIMARY KEY,
                        FIRST_NAME VARCHAR(256),
                        LAST_NAME VARCHAR(256),
                        EMAIL_ADDRESS VARCHAR(256),
                        PHONE_NUMBER VARCHAR(256)
);
CREATE TABLE LOGIN (
                       USER_ID SERIAL PRIMARY KEY,
                       USERNAME VARCHAR(256),
                       PASSWORD VARCHAR(256),
                       FOREIGN KEY (USER_ID) REFERENCES PERSON(PERSON_ID)
);

CREATE TABLE TASK (
                      TASK_ID SERIAL PRIMARY KEY,
                      PERSON_TASK_ID INTEGER,
                      DESCRIPTION VARCHAR(1024),
                      TASK_STATUS VARCHAR(10) DEFAULT 'FALSE',
                      FOREIGN KEY (PERSON_TASK_ID) REFERENCES PERSON(PERSON_ID)
);
