package com.listApp.listApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    Iterable<TaskList> findTaskListByTaskListPersonTaskId(Long personId);
    TaskList findTaskListByTaskListId(Long taskListId);
    List<TaskList> findTaskListByTaskListFavourite(Boolean isFavourite);

}
