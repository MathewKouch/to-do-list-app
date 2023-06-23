package com.listApp.listApp.business;

import com.listApp.listApp.data.Person;
import com.listApp.listApp.data.PersonRepository;
import com.listApp.listApp.data.Task;
import com.listApp.listApp.data.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;

    public PersonService(PersonRepository personRepository, TaskRepository taskRepository) {
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
    }

    public List<Task> getTaskByPersonID(Long personID) {
        Iterable<Task> tasks = this.taskRepository.findTaskByPersonTaskID(personID);
        Map<Long, Task> TaskMap = new HashMap();
        tasks.forEach(task -> {
            Task aTask = new Task();
            aTask.setTaskId(task.getTaskId());
            aTask.setParentListId(task.getParentListId());
            aTask.setTaskStatus(task.getTaskStatus());
            aTask.setPersonTaskID(task.getPersonTaskID());
            aTask.setDescription(task.getDescription());
            aTask.setDateCreated(task.getDateCreated());
            TaskMap.put(task.getTaskId(), aTask);
        });

        List<Task> allTasksByPersonId = new ArrayList<>();
        for (Long id : TaskMap.keySet()) {
            allTasksByPersonId.add(TaskMap.get(id));
        }

        return allTasksByPersonId;
    }

    public List<Task> getTaskByTaskListId(Long taskListId) {
        List<Task> tasks = this.taskRepository.findTaskByParentListId(taskListId);
        return tasks;
    }
    public List<Task> getAllTasks() {
        Iterable<Task> tasks = this.taskRepository.findAll();
        Map<Long, Task> TaskMap = new HashMap();
        tasks.forEach(task -> {
            Task aTask = new Task();
            aTask.setTaskId(task.getTaskId());
            aTask.setTaskStatus(task.getTaskStatus());
            aTask.setPersonTaskID(task.getPersonTaskID());
            aTask.setDescription(task.getDescription());
            aTask.setParentListId(task.getParentListId());
            aTask.setDateCreated(task.getDateCreated());
            TaskMap.put(task.getTaskId(), aTask);
        });

        List<Task> allTasks = new ArrayList<>();
        for (Long id : TaskMap.keySet()) {
            allTasks.add(TaskMap.get(id));
        }

        return allTasks;
    }

    public List<Person> getPersonById(Long personId) {
        Iterable<Person> persons = this.personRepository.findPersonByPersonId(personId);
        Map<Long, Person> PersonMap = new HashMap();
        persons.forEach(person -> {
            Person aPerson = new Person();
            aPerson.setPersonId(person.getPersonId());
            aPerson.setFirstName(person.getFirstName());
            aPerson.setLastName(person.getLastName());
            aPerson.setEmailAddress(person.getEmailAddress());
            aPerson.setPhoneNumber(person.getPhoneNumber());

            PersonMap.put(person.getPersonId(), aPerson);
        });

        List<Person> allPerson = new ArrayList<>();
        for (Long id : PersonMap.keySet()) {
            allPerson.add(PersonMap.get(id));
        }

        return allPerson;
    }

    public void addNewTask(Task task){
        this.taskRepository.save(task);
    }

    public Task getTaskByTaskId(Long taskId) {
        return this.taskRepository.findTaskByTaskId(taskId);
    }

    public List<Task> getTaskByParentListId(Long parentListId){
        return this.taskRepository.findTaskByParentListId(parentListId);
    }

    public void deleteTaskByTaskId(Long taskId){
        this.taskRepository.deleteById(taskId);
    }
}
