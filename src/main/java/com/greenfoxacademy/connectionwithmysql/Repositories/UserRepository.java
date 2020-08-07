package com.greenfoxacademy.connectionwithmysql.Repositories;

import com.greenfoxacademy.connectionwithmysql.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserById(Long Id);

    Optional<User> findUserByName(String name);
}
