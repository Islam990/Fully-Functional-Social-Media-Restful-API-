package com.islamgad.restapi.restapi.services;

import com.islamgad.restapi.restapi.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static List<User> userList = new ArrayList();
    private static int incrementalId = 0;


    public List<User> getAllUsers() {
        return userList;
    }

    public User getUserById(int id) {
        return userList.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public User save(User user) {
        user.setId(++incrementalId);
        userList.add(user);
        return user;
    }

    public void deleteUser(int id){
        userList.removeIf(user -> id == user.getId());
    }
}
