package com.greenfoxacademy.connectionwithmysql.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<Todo> todos = new HashSet<>();

}
