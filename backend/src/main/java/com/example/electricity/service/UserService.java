package com.example.electricity.service;

import com.example.electricity.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UserService() {
        // add a default user for demo
        users.put("demo", new User("demo", "demo123"));
    }

    public boolean register(String username, String password) {
        if (username == null || password == null) return false;
        if (users.containsKey(username)) return false;
        users.put(username, new User(username, password));
        return true;
    }

    public boolean validateCredentials(String username, String password) {
        if (username == null || password == null) return false;
        User u = users.get(username);
        return u != null && password.equals(u.getPassword());
    }
}
