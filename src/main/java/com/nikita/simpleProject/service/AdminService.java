package com.nikita.simpleProject.service;

import com.nikita.simpleProject.model.first.User;
import com.nikita.simpleProject.repository.firstrepository.UserRepository;
import com.nikita.simpleProject.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        UserUtils.removePassword(users);
        return users;
    }
}
