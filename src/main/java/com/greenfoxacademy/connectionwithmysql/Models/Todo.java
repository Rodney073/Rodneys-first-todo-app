package com.greenfoxacademy.connectionwithmysql.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String todo;
    private boolean urgent;
    private boolean done;
    private String description;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo(String title, User user) {
        this.todo = title;
        this.urgent = false;
        this.done = false;
        this.description = "";
        this.createDate = new Date();
        this.user = user;
    }

}
