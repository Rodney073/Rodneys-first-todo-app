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


    @Autowired
    public TodoController(TodoRepository todoRepository, TodoService todoService, UserService userService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String todoList(Model model) {
        model.addAttribute("todos", todoRepository.findAllByUser_Id(Integer.toUnsignedLong(1)));
        model.addAttribute("userName", userService.findUserById(Integer.toUnsignedLong(1)).getName());
        return "index";
    }


    @GetMapping("/create")
    public String createTodo(Model model, @RequestParam(required = false) String todo) {
        if (todo != null) {
            todoRepository.save(new Todo(todo, userService.findUserById(Integer.toUnsignedLong(1))));
            model.addAttribute("todos", todoService.findAll());
        } else {
            todoRepository.save(new Todo());
            model.addAttribute("todos", todoService.findAll());

        }
        return "index";
    }

    @PostMapping("/add-todo")
    public String setNutrition(Model model, @RequestParam String todo) {

        Todo newTodo = new Todo(todo, userService.findUserById(Integer.toUnsignedLong(1)));
        todoRepository.save(newTodo);
   /*     todoService.addUser_IdToTheLatestAddedTodo();
        todoRepository.save(newTodo);*/

        model.addAttribute("todos", todoService.findAll());

        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public String deleteTodo(Model model, @PathVariable Long id) {

        todoRepository.deleteById(id);
        //model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/";
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<Todo> list(Model model) {
        return todoService.findAll();
    }

    @GetMapping("/listbyid")
    @ResponseBody
    public Todo listById(@RequestParam Long id) {
        return todoService.findTodoById(id);

    }


    @GetMapping("/edit-todo/{id}")
    public String editTodo(Model model, @PathVariable Long id){
        model.addAttribute("todo", todoService.findTodoById(id));

        return "edit-todo";
    }

    @PostMapping("/update-todo/{id}")
    public String updateTodo(@PathVariable("id") Long id, @ModelAttribute Todo todo){

   /*     logger.info("got here");
        logger.info ("title is: "+todo.getTodo());
        logger.info ("done is: "+todo.isDone());
        logger.info ("urgent is: "+todo.isDone());
        logger.info ("description is: "+todo.getDescription());
        todoRepository.findTodoById(id).setTodo(todo.getTodo());
        todoRepository.findTodoById(id).setUrgent(todo.isUrgent());
        todoRepository.findTodoById(id).setDone(todo.isDone());*/

        //todoService.findTodoById(id).setUser(userService.findUserById(todoService.findTodoById(id).getUser().getId()));
        todoRepository.save(todo);

        //logger.info("finished update");

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        logger.info ("keyword is: "+keyword);
        logger.info ("keyword is: "+todoService.getTodoContainsKeyWord(keyword));
        model.addAttribute("todos", todoService.getTodoContainsKeyWord(keyword));

        return "index";
    }

}
