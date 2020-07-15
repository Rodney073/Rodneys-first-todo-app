package com.greenfoxacademy.connectionwithmysql.Models;

import javax.persistence.*;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String todo;
    private boolean urgent;
    private boolean done;

    public Todo() {
        todo = "Todo has not been specified";
        this.urgent = false;
        this.done = false;
    }


    public Todo(String title) {
        this.todo = title;
        this.urgent = false;
        this.done = false;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.todo = title;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    //Gettters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return todo;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public boolean isDone() {
        return done;
    }
}
