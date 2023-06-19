package com.listApp.listApp.web;

import com.listApp.listApp.business.PersonService;
import com.listApp.listApp.business.TaskListService;
import com.listApp.listApp.data.Task;
import com.listApp.listApp.data.TaskList;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping({"/home", "/index.html", "/", " "})
@SessionAttributes({"isLoggedIn", "userFirstName"})
public class HomeController {

    private final TaskListService taskListService;
    private final PersonService personService;

    public HomeController(TaskListService taskListService, PersonService personService) {
        this.taskListService = taskListService;
        this.personService = personService;
    }

    @RequestMapping
    public String renderHome(HttpSession session, Model model) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        Long personID = (Long) session.getAttribute("userID");

        model.addAttribute("isLoggedIn", isLoggedIn);

        if (personID != null && isLoggedIn!=null) {
            Long listId = taskListService.getPersonFavTaskList(personID);
            TaskList favTask = taskListService.getTaskListByTaskListId(listId);
            String favTaskName = favTask.getTaskListName();

            String userFirstName = (String) session.getAttribute("userFirstName");
            model.addAttribute("userFirstName", userFirstName);

            if (listId!=personID){
                List<Task> allTasks = this.personService.getTaskByTaskListId(listId);
                model.addAttribute("taskListId", listId);
                model.addAttribute("taskListName", favTaskName);
                model.addAttribute("tasks", allTasks );
            }
        }

        if (personID==null || isLoggedIn==null){
            session.setAttribute("isLoggedIn", false);
        }
        return "home_template";
    }

    @PostMapping("/updateFavTaskList")
    public String updateTask(
            @RequestParam(value="taskStatusList[]", required=false) List<String> taskStatusList,
            @RequestParam(value="taskDescription[]", required=false) List<String> taskDescription,
            @RequestParam("taskListId") Long listId){

        List<Task> allTasks =  this.personService.getTaskByTaskListId(listId);

        if (taskStatusList!=null){
            int i = 0;
            for (Task task: allTasks){
                Long taskIDLong = task.getTaskId();
                Task prevTask = personService.getTaskByTaskId(taskIDLong);

                String taskID = taskIDLong.toString();

                if (taskStatusList.contains(taskID)){
                    prevTask.setTaskStatus("COMPLETE");
                } else {
                    prevTask.setTaskStatus("INCOMPLETE");
                }
                personService.addNewTask(prevTask);
                i++;
            }
        } else {
            int i = 0;
            for (Task task: allTasks){
                Long taskIDLong = task.getTaskId();
                Task prevTask = personService.getTaskByTaskId(taskIDLong);
                prevTask.setTaskStatus("INCOMPLETE");
                personService.addNewTask(prevTask);
                i++;
            }
        }

        if (taskDescription!=null){
            int i = 0;
            for (Task task2: allTasks){
                Long taskIDLong2 = task2.getTaskId();
                Task prevTask2 = personService.getTaskByTaskId(taskIDLong2);

                String newDesc = taskDescription.get(i);
                prevTask2.setDescription(newDesc);
                personService.addNewTask(prevTask2);
                i++;
            }
        }

//        return "redirect:/home_template?taskListId=" + listId.toString();
        return "redirect:/home";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam("description") String description,
                          @RequestParam("taskListId") Long listId,
                          HttpSession session){
        Long userID = (Long) session.getAttribute("userID");

        Task newTask = new Task();
        newTask.setPersonTaskID(userID);
        newTask.setParentListId(listId);
        newTask.setDescription(description);
        newTask.setTaskStatus("INCOMPLETE");

        personService.addNewTask(newTask);
        return "redirect:/home";

    }
    @GetMapping("/sign-out")
    public String signOut(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Clear the session attributes
        return "redirect:/login"; // Redirect to the login page
    }
}
