package com.greenfoxacademy.connectionwithmysql.Service;

import com.greenfoxacademy.connectionwithmysql.Models.Todo;
import com.greenfoxacademy.connectionwithmysql.Repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;
    private UserService userService;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    public Iterable <Todo> findAll () {
        return todoRepository.findAll();
    }

    public List<Todo> getTodoContainsKeyWord(String todo){
        return todoRepository.findTodoByTodoContains (todo);
    }

    public List<Todo> findAllByUser_Id(Long id){
        return todoRepository.findAllByUser_Id (id);
    }

    public Todo findTodoById (Long id){
        return todoRepository.findTodoById(id);
    }

    public void addUser_IdToTheLatestAddedTodo(Long id) {
        todoRepository.findTopByOrderByIdDesc().setUser(userService.findUserById(id));
    }

    public void findTodoByIdAndSetUserId (Long todo_id, Long user_id){
        todoRepository.findTodoById(todo_id).setUser(userService.findUserById(user_id));
    }

    public void updateTodo(String title, String description, boolean urgent, boolean done, Todo todo) {
        todo.setTodo(title);
        todo.setDescription(description);
        todo.setUrgent(urgent);
        todo.setDone(done);
        todoRepository.save(todo);
    }

    public void deleteById (Long id) {
        todoRepository.deleteById(id);
    }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

}
