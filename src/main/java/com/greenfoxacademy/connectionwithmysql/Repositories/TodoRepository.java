package com.greenfoxacademy.connectionwithmysql.Repositories;

import com.greenfoxacademy.connectionwithmysql.Models.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findAllByTodo(String name);
    Todo findTodoById(Long id);
    List<Todo> findTodoByTodoContains (String todo);


}
