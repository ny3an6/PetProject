package com.nikita.simpleProject.utils;


import com.nikita.simpleProject.model.first.User;

import java.util.List;

public class UserUtils {
    public static void removePassword(User user){
        user.setPassword("");
    }

    public static void removePassword(List<User> users){
        //users.forEach(u -> u.setPassword(""));
        for (User user : users){
            user.setPassword("");
        }
    }
}
