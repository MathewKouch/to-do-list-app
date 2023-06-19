package com.listApp.listApp.business;

import com.listApp.listApp.data.Task;
import com.listApp.listApp.data.TaskList;
import com.listApp.listApp.data.TaskListRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public List<TaskList> getTaskListByPersonID(Long personID) {
        // Return a list of task lists belonging to a person id.
        Iterable<TaskList> listOfTasks = this.taskListRepository.findTaskListByTaskListPersonTaskId(personID);
        Map<Long, TaskList> TaskListMap = new HashMap();
        listOfTasks.forEach(tasklist -> {
            TaskList aTaskList = new TaskList();
            aTaskList.setTaskListId(tasklist.getTaskListId());
            aTaskList.setTaskListPersonTaskId(tasklist.getTaskListPersonTaskId());
            aTaskList.setTaskListName(tasklist.getTaskListName());
            aTaskList.setTaskListFavourite(tasklist.isTaskListFavourite());
            aTaskList.setDateCreated(tasklist.getDateCreated());
            TaskListMap.put(tasklist.getTaskListId(), aTaskList);
        });

        List<TaskList> allTaskListsByPersonTaskId = new ArrayList<>();
        for (Long id : TaskListMap.keySet()) {
            allTaskListsByPersonTaskId.add(TaskListMap.get(id));
        }

        return allTaskListsByPersonTaskId;
    }

    public Boolean starTaskList(Long taskListId, Long personId) {
        // Set the task list as favourite. Only one task list can be favorite.

        // Grab all favourited tasklists (there should only be one at all time)
        Iterable<TaskList> allLists = this.taskListRepository.findTaskListByTaskListPersonTaskId(personId);

        // Unfavourite all lists
        for (TaskList afavList: allLists) {
            Boolean isFav = false;
            afavList.setTaskListFavourite(isFav);
            this.taskListRepository.save(afavList);
        }

        // Favourite this one list
        TaskList favouriteTaskList = this.taskListRepository.findTaskListByTaskListId(taskListId);
        if (favouriteTaskList==null){
            return false;
        } else {
            Boolean isTrue = true;
            favouriteTaskList.setTaskListFavourite(isTrue);
            this.taskListRepository.save(favouriteTaskList);
            return true;
        }
    }

    public boolean deleteTaskListById(Long taskListId) {
        // finds if the task to be deleted exists and return true if deleted or false if error
        TaskList aTaskList = taskListRepository.findTaskListByTaskListId(taskListId);
        if (aTaskList != null) {
            taskListRepository.deleteById(taskListId);
            return true;
        } else {
            return false;
        }
    }

    public void createTaskList(String taskListName, Long personId){
        TaskList newTaskList = new TaskList();
        newTaskList.setTaskListName(taskListName);
        newTaskList.setTaskListPersonTaskId(personId);
        this.taskListRepository.save(newTaskList);
    }

    public void saveTaskList(TaskList taskList){
        this.taskListRepository.save(taskList);
    }

    public void addNewTaskList(TaskList taskList){
        this.taskListRepository.save(taskList);
    }

    public Long getPersonFavTaskList(Long userId){
        List<TaskList> fav = this.taskListRepository.findTaskListByTaskListFavourite(true);
        for (TaskList aTaskList: fav){
            if (aTaskList.getTaskListPersonTaskId()==userId){
                return aTaskList.getTaskListId();
            }
        }
        return userId;
    }

    public TaskList getTaskListByTaskListId(Long id){
        return this.taskListRepository.findTaskListByTaskListId(id);
    }

}
