package com.listApp.listApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Iterable<Task> findTaskByPersonTaskID(Long personID);
    Task findTaskByTaskId(Long taskId);
    List<Task> findTaskByParentListId(Long listId);

}
