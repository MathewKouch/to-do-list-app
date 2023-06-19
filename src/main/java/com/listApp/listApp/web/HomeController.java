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

//    @RequestMapping
//    public String renderHome(@RequestParam(value="taskListId", required=false) Long taskListId,
//                              Model model, HttpSession session) {
//        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
//        Long personID = (Long) session.getAttribute("userID");
//
//        if (personID != null && isLoggedIn && taskListId!=null) {
//            List<Task> allTasks = this.personService.getTaskByTaskListId(taskListId);
//            TaskList thisTaskList = this.taskListService.getTaskListByTaskListId(taskListId);
//
//            String taskListName = thisTaskList.getTaskListName();
//            sortTasks(allTasks);
//
//            model.addAttribute("tasks", allTasks);
//            model.addAttribute("taskListId", taskListId);
//            model.addAttribute("taskListName", taskListName);
//        }
//
//        if (isLoggedIn!=null) {
//            session.setAttribute("isLoggedIn", isLoggedIn);
//            return "home_template";
//        } else {
//            session.setAttribute("isLoggedIn", false);
//            return "home_template";
//        }
//    }

    @GetMapping("/sign-out")
    public String signOut(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Clear the session attributes
        return "redirect:/login"; // Redirect to the login page
    }
}
