package com.greenfoxacademy.connectionwithmysql.Service;

import com.greenfoxacademy.connectionwithmysql.Models.User;
import com.greenfoxacademy.connectionwithmysql.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public boolean isPasswordOk(String name, String password) {
        if (userRepository.findUserByName(name).get().getPassword().equals(password)) {
            return true;
        } else return false;
    }

    public Long getUserId(String name) {
        return userRepository.findUserByName(name).get().getId();
    }

    public void save(User user) {
        userRepository.save(user);
    }


}
