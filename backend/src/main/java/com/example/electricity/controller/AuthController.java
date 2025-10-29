package com.example.electricity.controller;

import com.example.electricity.service.TokenService;
import com.example.electricity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        boolean ok = userService.register(username, password);
        if (!ok) return ResponseEntity.badRequest().body(Map.of("error", "username-taken-or-invalid"));
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (!userService.validateCredentials(username, password)) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid_credentials"));
        }
        String token = tokenService.createTokenForUser(username);
        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("username", username);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(@RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            String username = tokenService.getUsernameForToken(token);
            if (username != null) {
                return ResponseEntity.ok(Map.of("loggedIn", true, "username", username));
            }
        }
        return ResponseEntity.ok(Map.of("loggedIn", false));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization", required = false) String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            tokenService.invalidateToken(token);
        }
        return ResponseEntity.ok(Map.of("success", true));
    }
}
