package com.example.electricity.service;

import com.example.electricity.model.Complaint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ComplaintService {
    private final Map<String, List<Complaint>> complaintsByUser = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Complaint registerComplaint(String username, String message) {
        Complaint c = new Complaint(idGen.getAndIncrement(), username, message, "OPEN");
        complaintsByUser.computeIfAbsent(username, k -> new ArrayList<>()).add(c);
        return c;
    }

    public List<Complaint> getComplaintsForUser(String username) {
        return complaintsByUser.getOrDefault(username, new ArrayList<>());
    }
}
