package com.greenfoxacademy.connectionwithmysql.Controllers;

import com.greenfoxacademy.connectionwithmysql.Models.Todo;
import com.greenfoxacademy.connectionwithmysql.Service.TodoService;
import com.greenfoxacademy.connectionwithmysql.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    //private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;
    private final UserService userService;

    Long actualUserId = null;


    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }


    @GetMapping("/")
    public String todoList(Model model, @RequestParam Long user_id) {
        actualUserId = user_id;

        model.addAttribute("todos", todoService.findAllByUser_Id(user_id));
        model.addAttribute("userName", userService.findUserById(user_id).getName());

        return "index";
    }


    @PostMapping("/add-todo")
    public String addTodo(@RequestParam String todo) {

        Todo newTodo = new Todo(todo, userService.findUserById(actualUserId));
        todoService.addUser_IdToTheLatestAddedTodo(actualUserId);
        todoService.save(newTodo);

        return "redirect:/?user_id=" + actualUserId;
    }


    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {

        todoService.deleteById(id);

        return "redirect:/?user_id=" + actualUserId;
    }


    @GetMapping("/edit-todo/{id}")
    public String editTodo(Model model, @PathVariable Long id) {

        model.addAttribute("todo", todoService.findTodoById(id));

        return "edit-todo";
    }

    @PostMapping("/update-todo/{id}")
    public String updateTodo(@ModelAttribute Todo todo) {

        todoService.updateTodo(todo.getTodo(), todo.getDescription(), todo.isUrgent(), todo.isDone(),
                todoService.findTodoById(todo.getId()));

        return "redirect:/?user_id=" + actualUserId;
    }


    @GetMapping("/search")
    public String searchTodo(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        model.addAttribute("todos", todoService.getTodoContainsKeyWord(keyword));
        model.addAttribute("userName", userService.findUserById(actualUserId).getName());

        return "index";
    }

    @GetMapping("/reset")
    public String resetList() {
        return "redirect:/?user_id=" + actualUserId;
    }




/*    @GetMapping("/list")
    @ResponseBody
    public Iterable<Todo> list(Model model) {
        return todoService.findAll();
    }

    @GetMapping("/listbyid")
    @ResponseBody
    public Todo listById(@RequestParam Long id) {
        return todoService.findTodoById(id);

    }

        logger.info("got here");

        logger.info("keyword is: " + keyword);
        logger.info("keyword is: " + todoService.getTodoContainsKeyWord(keyword));


        logger.info ("title is: "+todo.getTodo());
        logger.info ("done is: "+todo.isDone());
        logger.info ("urgent is: "+todo.isDone());
        logger.info ("description is: "+todo.getDescription());
        todoRepository.findTodoById(id).setTodo(todo.getTodo());
        todoRepository.findTodoById(id).setUrgent(todo.isUrgent());
        todoRepository.findTodoById(id).setDone(todo.isDone());*/

    //logger.info("finished update");

}
