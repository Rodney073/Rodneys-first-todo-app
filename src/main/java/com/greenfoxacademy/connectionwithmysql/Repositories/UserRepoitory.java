package com.greenfoxacademy.connectionwithmysql.Repositories;

import com.greenfoxacademy.connectionwithmysql.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoitory extends CrudRepository <User, Long> {

}
