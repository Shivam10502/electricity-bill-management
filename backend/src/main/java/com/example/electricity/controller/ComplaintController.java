package com.example.electricity.controller;

import com.example.electricity.model.Complaint;
import com.example.electricity.service.ComplaintService;
import com.example.electricity.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComplaintController {

    private final ComplaintService complaintService;
    private final TokenService tokenService;

    public ComplaintController(ComplaintService complaintService, TokenService tokenService) {
        this.complaintService = complaintService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestHeader(name = "Authorization", required = false) String auth,
                                      @RequestBody Map<String, String> body) {
        String username = null;
        if (auth != null && auth.startsWith("Bearer ")) {
            username = tokenService.getUsernameForToken(auth.substring(7));
        }
        if (username == null) return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));
        String message = body.get("message");
        if (message == null || message.isBlank()) return ResponseEntity.badRequest().body(Map.of("error", "missing_message"));
        Complaint c = complaintService.registerComplaint(username, message);
        return ResponseEntity.ok(c);
    }

    @GetMapping("/my")
    public ResponseEntity<?> myComplaints(@RequestHeader(name = "Authorization", required = false) String auth) {
        String username = null;
        if (auth != null && auth.startsWith("Bearer ")) {
            username = tokenService.getUsernameForToken(auth.substring(7));
        }
        if (username == null) return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));
        List<Complaint> list = complaintService.getComplaintsForUser(username);
        return ResponseEntity.ok(list);
    }
}
