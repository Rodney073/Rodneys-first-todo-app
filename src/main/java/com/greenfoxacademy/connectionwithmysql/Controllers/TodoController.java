package com.greenfoxacademy.connectionwithmysql.Controllers;

import com.greenfoxacademy.connectionwithmysql.Models.Todo;
import com.greenfoxacademy.connectionwithmysql.Models.User;
import com.greenfoxacademy.connectionwithmysql.Repositories.TodoRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoRepository todoRepository;
    private final TodoService todoService;
    private final UserService userService;

    Long actualUserId = null;


    @Autowired
    public TodoController(TodoRepository todoRepository, TodoService todoService, UserService userService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String todoList(Model model, @RequestParam Long user_id) {
        actualUserId = user_id;
        model.addAttribute("todos", todoRepository.findAllByUser_Id(user_id));
        model.addAttribute("userName", userService.findUserById(user_id).getName());
        model.addAttribute("userId", actualUserId);
        return "index";
    }


    @PostMapping("/add-todo")
    public String addTodo(Model model, @RequestParam String todo) {

        Todo newTodo = new Todo(todo, userService.findUserById(actualUserId));
        todoRepository.save(newTodo);

        todoService.addUser_IdToTheLatestAddedTodo(actualUserId);
        todoRepository.save(newTodo);

        model.addAttribute("todos", todoService.findAll());

        return "redirect:/?user_id=" + actualUserId;
    }


    //Bug: this method delete both the selected Todo and the User.?!?!?!?!
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/?user_id=" + actualUserId;
    }


    @GetMapping("/edit-todo/{id}")
    public String editTodo(Model model, @PathVariable Long id){
        model.addAttribute("todo", todoService.findTodoById(id));
        //model.addAttribute("userName", userService.findUserById(user_id).getName());

        return "edit-todo";
    }

    @PostMapping("/update-todo/{id}")
    public String updateTodo(@ModelAttribute Todo todo){

        todoService.updateTodo(todo.getTodo(), todo.getDescription(), todo.isUrgent(), todo.isDone(),
                todoRepository.findTodoById(todo.getId()));

   /*     logger.info("got here");
        logger.info ("title is: "+todo.getTodo());
        logger.info ("done is: "+todo.isDone());
        logger.info ("urgent is: "+todo.isDone());
        logger.info ("description is: "+todo.getDescription());
        todoRepository.findTodoById(id).setTodo(todo.getTodo());
        todoRepository.findTodoById(id).setUrgent(todo.isUrgent());
        todoRepository.findTodoById(id).setDone(todo.isDone());*/

        //todoService.findTodoById(id).setUser(userService.findUserById(todoService.findTodoById(id).getUser().getId()));
        //todoRepository.save(todo);

        //logger.info("finished update");

        return "redirect:/?user_id=" + actualUserId;
    }

    @GetMapping("/search")
    public String searchTodo(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        logger.info ("keyword is: "+keyword);
        logger.info ("keyword is: "+todoService.getTodoContainsKeyWord(keyword));
        model.addAttribute("todos", todoService.getTodoContainsKeyWord(keyword));

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

    }*/

}
