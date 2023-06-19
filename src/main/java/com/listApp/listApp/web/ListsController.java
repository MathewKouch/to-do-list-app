package com.listApp.listApp.web;

import com.listApp.listApp.business.PersonService;
import com.listApp.listApp.business.TaskListService;
import com.listApp.listApp.data.Task;
import com.listApp.listApp.data.TaskList;
import com.listApp.listApp.data.TaskListRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/lists")
@SessionAttributes({"isLoggedIn", "userID"})
public class ListsController {

    private final TaskListService taskListService;

    private final PersonService personService;

    public ListsController(TaskListService taskListService, PersonService personService) {
        this.taskListService = taskListService;
        this.personService = personService;
    }

    @RequestMapping
    public String renderLists(Model model, HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        Long personID = (Long) session.getAttribute("userID");

        if (personID != null && isLoggedIn) {
            List<TaskList> allTaskList = this.taskListService.getTaskListByPersonID(personID);
//            sortLists(allTaskList);  // Sort the tasks
            model.addAttribute("tasklists", allTaskList);

            return "lists";
        } else {
            return "redirect:/access_denied.html";
        }
    }

    @PostMapping("/addTaskList")
    public String addTaskList(@RequestParam("taskListName") String listName,
                          HttpSession session){
        Long userID = (Long) session.getAttribute("userID");
        taskListService.createTaskList(listName, userID);
        return "redirect:/lists";
    }

    private void sortLists(List<TaskList> taskLists) {
        Comparator<TaskList> comparator = Comparator
                .comparing(TaskList::getDateCreated);
        taskLists.sort(comparator);
    }

    @PostMapping("/updateTaskList")
    public String updateTask(
            @RequestParam(value="taskFavourited[]", required=false) List<String> taskListIdFav,
            @RequestParam(value="taskListName[]", required=false) List<String> taskListName,
            HttpSession session){

        Long userID = (Long) session.getAttribute("userID");
        List<TaskList> allTaskLists = this.taskListService.getTaskListByPersonID(userID);

        // Updating favourite
        if (taskListIdFav!=null){

            for (TaskList aTaskList: allTaskLists){
                Long aTaskListIdLong = aTaskList.getTaskListId();
                String aTaskListId = aTaskListIdLong.toString();

                if (taskListIdFav.contains(aTaskListId)){
                    aTaskList.setTaskListFavourite(true);
                } else{
                    aTaskList.setTaskListFavourite(false);
                }
                taskListService.addNewTaskList(aTaskList);
            }

        } else {
            return "redirect:/access_denied.html";
        }

        // Updating list names
        if (taskListName!=null) {
            int i = 0;
            for (TaskList taskList : allTaskLists) {
                String newTaskListName = taskListName.get(i);
                taskList.setTaskListName(newTaskListName);
                taskListService.saveTaskList(taskList);
                i++;
            }
        }
        return "redirect:/lists";
    }
}
