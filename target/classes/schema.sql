CREATE TABLE PERSON (
                        PERSON_ID SERIAL PRIMARY KEY,
                        FIRST_NAME VARCHAR(256),
                        LAST_NAME VARCHAR(256),
                        EMAIL_ADDRESS VARCHAR(256),
                        PHONE_NUMBER VARCHAR(256),
                        USER_PASSWORD VARCHAR(256)

);

CREATE TABLE LOGIN (
                       USERNAME VARCHAR(256) PRIMARY KEY,
                       USER_PASSWORD VARCHAR(256),
                       USER_ID SERIAL,
                       FOREIGN KEY (USER_ID) REFERENCES PERSON(PERSON_ID)
);

CREATE TABLE TASK_LIST (
                           TASK_LIST_ID SERIAL PRIMARY KEY ,
                           TASK_LIST_PERSON_TASK_ID INTEGER,
                           TASK_LIST_NAME VARCHAR(32),
                           TASK_LIST_FAVOURITE BOOLEAN DEFAULT false,
                           FOREIGN KEY (TASK_LIST_PERSON_TASK_ID) REFERENCES PERSON(PERSON_ID)
);

CREATE TABLE TASK (
                      TASK_ID SERIAL PRIMARY KEY,
                      PARENT_LIST_ID INTEGER,
                      PERSON_TASK_ID INTEGER,
                      DESCRIPTION VARCHAR(1024),
                      TASK_STATUS VARCHAR(10) DEFAULT 'INCOMPLETE',
                      FOREIGN KEY (PERSON_TASK_ID) REFERENCES PERSON(PERSON_ID),
                      FOREIGN KEY (PARENT_LIST_ID) REFERENCES TASK_LIST(TASK_LIST_ID)
);

ALTER TABLE task
    ADD COLUMN date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE task_list
    ADD COLUMN date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

CREATE SEQUENCE person_seq START 100;
CREATE SEQUENCE task_seq START 100;
CREATE SEQUENCE task_list_seq START 100;


