package com.greenfoxacademy.connectionwithmysql.Controllers;

import com.greenfoxacademy.connectionwithmysql.Models.Todo;
import com.greenfoxacademy.connectionwithmysql.Repositories.TodoRepository;
import com.greenfoxacademy.connectionwithmysql.Service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private TodoRepository todoRepository;

    private TodoService todoService;


    @Autowired
    public TodoController(TodoRepository todoRepository, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
    }

    @GetMapping("/todolist")
    public String todoList(Model model) {
        model.addAttribute("todos", todoRepository.findAll());

        return "todolist";
    }


    @GetMapping("/create")
    public String createTodo(Model model, @RequestParam(required = false) String todo) {
        if (todo != null) {
            todoRepository.save(new Todo(todo));
            model.addAttribute("todos", todoRepository.findAll());
        } else {
            todoRepository.save(new Todo());
            model.addAttribute("todos", todoRepository.findAll());

        }
        return "todolist";
    }

    @PostMapping("/add-todo")
    public String setNutrition(Model model, @RequestParam String todo) {

        todoRepository.save(new Todo(todo));
        model.addAttribute("todos", todoRepository.findAll());

        return "redirect:/todolist";
    }


    @GetMapping("/delete/{id}")
    public String deleteTodo(Model model, @PathVariable Long id) {

        todoRepository.deleteById(id);
        //model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/todolist";
    }

    @GetMapping("/list")
    @ResponseBody
    public Iterable<Todo> list(Model model) {
        return todoRepository.findAll();
    }

    @GetMapping("/listbyid")
    @ResponseBody
    public Todo listById(@RequestParam Long id) {
        return todoRepository.findTodoById(id);

    }


    @GetMapping("/edit-todo/{id}")
    public String editTodo(Model model, @PathVariable Long id){
        model.addAttribute("todo", todoRepository.findTodoById(id));

        return "edit-todo";
    }

    @PostMapping("/update-todo/{id}")
    public String updateTodo(@PathVariable("id") Long id, @ModelAttribute Todo todo){

        logger.info("got here");
        logger.info ("title is: "+todo.getTitle());
        logger.info ("done is: "+todo.isDone());
        logger.info ("urgent is: "+todo.isDone());
        //todoRepository.findTodoById(id).setTitle(todo.getTitle());
        //todoRepository.findTodoById(id).setUrgent(todo.isUrgent());
        //todoRepository.findTodoById(id).setDone(todo.isDone());
        todoRepository.save(todo);
        logger.info("finished update");

        return "redirect:/todolist";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        logger.info ("keyword is: "+keyword);
        logger.info ("keyword is: "+todoService.getTodoContainsKeyWord(keyword));
        model.addAttribute("todos", todoService.getTodoContainsKeyWord(keyword));

        return "todolist";
    }

}
