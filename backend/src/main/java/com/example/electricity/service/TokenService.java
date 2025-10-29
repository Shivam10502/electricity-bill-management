package com.example.electricity.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    // token -> username
    private final Map<String, String> tokens = new ConcurrentHashMap<>();

    public String createTokenForUser(String username) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, username);
        return token;
    }

    public String getUsernameForToken(String token) {
        return tokens.get(token);
    }

    public void invalidateToken(String token) {
        tokens.remove(token);
    }
}
