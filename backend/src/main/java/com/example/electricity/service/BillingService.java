package com.example.electricity.service;

import com.example.electricity.model.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BillingService {
    private final Map<String, List<Bill>> billsByUser = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // sample data
        List<Bill> demoBills = new ArrayList<>();
        demoBills.add(new Bill(idGen.getAndIncrement(), "demo", "2025-09", 75.50, false));
        demoBills.add(new Bill(idGen.getAndIncrement(), "demo", "2025-08", 63.20, true));
        billsByUser.put("demo", demoBills);
    }

    public List<Bill> getBillsForUser(String username) {
        return billsByUser.getOrDefault(username, new ArrayList<>());
    }

    public boolean payBill(String username, long billId) {
        List<Bill> bills = billsByUser.get(username);
        if (bills == null) return false;
        for (Bill b : bills) {
            if (b.getId() == billId) {
                if (!b.isPaid()) {
                    b.setPaid(true);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
