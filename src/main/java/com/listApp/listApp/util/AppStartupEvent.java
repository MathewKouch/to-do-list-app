package com.listApp.listApp.util;

import com.listApp.listApp.data.Task;
import com.listApp.listApp.data.TaskRepository;
import com.listApp.listApp.data.Person;
import com.listApp.listApp.data.PersonRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final PersonRepository personRepository;

    private final TaskRepository taskRepository;

    public AppStartupEvent(PersonRepository personRepository, TaskRepository taskRepository) {
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Person> persons = this.personRepository.findAll();
        persons.forEach(System.out::println);

        Iterable<Task> tasks = this.taskRepository.findAll();
        tasks.forEach(System.out::println);

    }

}
