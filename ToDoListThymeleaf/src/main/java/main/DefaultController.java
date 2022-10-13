package main;
import main.Dao.TaskDao;
import main.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class DefaultController {

    @Autowired
   private TaskDao taskDao;

    @GetMapping("/")
    public String index(Model model) {

       Iterable<Task> taskIterable = taskDao.findAll();
       List<Task> tasks = new ArrayList<>();
       for (Task task : taskIterable) {
           tasks.add(task);
       }

       model.addAttribute("tasks", tasks);
       model.addAttribute("taskCount", tasks.size());

        return "index";
    }

}
