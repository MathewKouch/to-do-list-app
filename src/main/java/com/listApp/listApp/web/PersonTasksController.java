package com.listApp.listApp.web;

import com.listApp.listApp.business.PersonService;
import com.listApp.listApp.business.TaskListService;
import com.listApp.listApp.data.Task;
import com.listApp.listApp.data.TaskList;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/tasks")
@SessionAttributes({"isLoggedIn", "userID"})
public class PersonTasksController {

    private final PersonService personService;
    private final TaskListService taskListService;

    public PersonTasksController(PersonService personService, TaskListService taskListService) {
        this.personService = personService;
        this.taskListService = taskListService;
    }

    @RequestMapping
    public String renderTasks(@RequestParam(value="taskListId", required=false) Long taskListId,
                              Model model, HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        Long personID = (Long) session.getAttribute("userID");

        if (personID != null && isLoggedIn && taskListId!=null) {
            List<Task> allTasks = this.personService.getTaskByTaskListId(taskListId);
            TaskList thisTaskList = this.taskListService.getTaskListByTaskListId(taskListId);

            String taskListName = thisTaskList.getTaskListName();
//            sortTasks(allTasks);

            model.addAttribute("tasks", allTasks);
            model.addAttribute("taskListId", taskListId);
            model.addAttribute("taskListName", taskListName);

            return "to-do-list";
        } else if (personID != null && isLoggedIn && taskListId==null) {
            return "redirect:/lists.html";
        } else {
            return "redirect:/access_denied.html";
        }
    }

    private void sortTasks(List<Task> tasks) {
        Comparator<Task> comparator = Comparator
                .comparing(Task::getDateCreated)
                .thenComparing(Comparator.comparing(Task::getTaskStatus).reversed());

        tasks.sort(comparator);
    }

    @RequestMapping("/edit")
    public String renderEditTasks(@RequestParam(value="taskListId", required=false) Long taskListId,
                              Model model, HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        Long personID = (Long) session.getAttribute("userID");

        if (personID != null && isLoggedIn && taskListId!=null) {
            List<Task> allTasks = this.personService.getTaskByTaskListId(taskListId);
            TaskList thisTaskList = this.taskListService.getTaskListByTaskListId(taskListId);

            String taskListName = thisTaskList.getTaskListName();

            model.addAttribute("tasks", allTasks);
            model.addAttribute("taskListId", taskListId);
            model.addAttribute("taskListName", taskListName);

            return "edit_to-do-list";
        } else if (personID != null && isLoggedIn && taskListId==null) {
            return "redirect:/lists.html";
        } else {
            return "redirect:/access_denied.html";
        }
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
        return "redirect:/tasks?taskListId=" + listId.toString();
    }

    @PostMapping("/updateTask")
    public String updateTask(
          @RequestParam(value="taskStatusList[]", required=false) List<String> taskStatusList,
          @RequestParam(value="taskDescription[]", required=false) List<String> taskDescription,
          @RequestParam(value="deleteTasks[]", required=false) List<String> deleteTasks,

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

        if (deleteTasks!=null){
            for (String taskIdDeleted: deleteTasks){
                Long taskIdDeletedLong = Long.valueOf(taskIdDeleted);
                personService.deleteTaskByTaskId(taskIdDeletedLong);
            }
        }
        return "redirect:/tasks?taskListId=" + listId.toString();
    }

    @GetMapping("/show_favourite")
    public String renderFavouriteTaskList(HttpSession session){
        Long userID = (Long) session.getAttribute("userID");
        Long listId = taskListService.getPersonFavTaskList(userID);
        if (listId!=userID){
            return "redirect:/tasks?taskListId=" + listId.toString();
        }
        return "redirect:/lists";
    }
}
