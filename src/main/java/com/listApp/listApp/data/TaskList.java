package com.listApp.listApp.data;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="TASK_LIST")
public class TaskList {

    @Id
    @Column(name="TASK_LIST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskListId;

    @Column(name = "TASK_LIST_PERSON_TASK_ID")
    private long taskListPersonTaskId;

    @Column(name = "TASK_LIST_NAME")
    private String taskListName;

    @Column(name = "TASK_LIST_FAVOURITE")
    private boolean taskListFavourite;

    @Column(name = "DATE_CREATED")
    private Date dateCreated;
    public long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(long taskListId) {
        this.taskListId = taskListId;
    }


    public long getTaskListPersonTaskId() {
        return taskListPersonTaskId;
    }

    public void setTaskListPersonTaskId(long taskListPersonTaskId) {
        this.taskListPersonTaskId = taskListPersonTaskId;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTaskListName() {
        return taskListName;
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;
    }

    public boolean isTaskListFavourite() {
        return taskListFavourite;
    }

    public void setTaskListFavourite(boolean taskListFavourite) {
        this.taskListFavourite = taskListFavourite;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "taskListId=" + taskListId +
                ", taskListPersonTaskId=" + taskListPersonTaskId +
                ", taskListName='" + taskListName + '\'' +
                ", taskListFavourite=" + taskListFavourite +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
