package com.listApp.listApp.data;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Table(name="TASK")
public class Task {
    @Id
    @Column(name="TASK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Changed from AUTO to IDENTITY
    private long taskId;

    @Column(name="PERSON_TASK_ID")
    private long personTaskID;

    @Column(name="PARENT_LIST_ID")
    private long parentListId;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="TASK_STATUS")
    private String taskStatus;

    @CreationTimestamp
    @Column(name="DATE_CREATED")
    private Date dateCreated;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getPersonTaskID() {
        return personTaskID;
    }

    public void setPersonTaskID(long personTaskID) {
        this.personTaskID = personTaskID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getParentListId() {
        return parentListId;
    }

    public void setParentListId(long parentListId) {
        this.parentListId = parentListId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", personTaskID=" + personTaskID +
                ", parentListId=" + parentListId +
                ", description='" + description + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
