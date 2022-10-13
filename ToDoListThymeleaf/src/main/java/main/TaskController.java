package main;

import main.Dao.TaskDao;
import main.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@RestController
public class TaskController {

    @Autowired
    private TaskDao taskDao;

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable Integer id) {

            Optional newObject = taskDao.findById(id);
            if (!newObject.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND");
            }else {
            return new ResponseEntity<>(newObject, HttpStatus.OK);
        }
    }

        @GetMapping("/tasks/")
        public List<Task> list () {

            Iterable<Task> taskIterable = taskDao.findAll();
            List<Task> taskList = new ArrayList<>();
            for(Task task : taskIterable) {
                taskList.add(task);
            }
            return taskList;
        }

        @PostMapping("/tasks/")
        @Transactional
        public ResponseEntity addTask (@Valid @RequestBody Task task){
            taskDao.save(task);
            return ResponseEntity.ok(task);
        }

        @PutMapping("/tasks/{id}")
        @Transactional
        public ResponseEntity putTask (@PathVariable Integer id,
                @Valid @RequestBody Task taskDetails){

           Optional taskOptional = taskDao.findById(id);

            if (!taskOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task is not found by ID - " + id);
            }
            Task newTask = new Task();
            newTask.setId(id);
            newTask.setDate(taskDetails.getDate());
            newTask.setName(taskDetails.getName());
            taskDao.save(newTask);
            return ResponseEntity.ok(taskDetails);
        }

        @PutMapping("/tasks/")
        @Transactional
        public ResponseEntity<?> putAllTask (@Valid @RequestBody Task taskDetails){

            try {
                Iterable<Task> iterableTask = taskDao.findAll();
                for (Task task : iterableTask) {
                    Task newTask = new Task();
                    newTask.setId(taskDetails.getId());
                    newTask.setDate(taskDetails.getDate());
                    newTask.setName(taskDetails.getName());
                    taskDao.save(newTask);
                }
                return ResponseEntity.ok("Список дел обновлен");
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Дела не найдены");
            }
        }

        @DeleteMapping("/tasks/{id}")
        @Transactional
        public ResponseEntity deleteTask (@PathVariable Integer id) throws IllegalArgumentException {

            Optional taskToRemove = taskDao.findById(id);

            if (!taskToRemove.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Дело не найдено!");
            }

            taskDao.delete((Task) taskToRemove.get());
            return ResponseEntity.ok("Дело успешно удалено");
        }

        @DeleteMapping("/tasks/")
        @Transactional
        public ResponseEntity deleteTasks () {

            Iterable<Task> optionalTask = taskDao.findAll();
            for (Task task : optionalTask) {
                taskDao.delete(task);
            }
            return ResponseEntity.ok("Все дела успешно удалены");
        }

    }
