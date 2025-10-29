package com.example.electricity.controller;

import com.example.electricity.model.Bill;
import com.example.electricity.service.BillingService;
import com.example.electricity.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

    private final BillingService billingService;
    private final TokenService tokenService;

    public PaymentController(BillingService billingService, TokenService tokenService) {
        this.billingService = billingService;
        this.tokenService = tokenService;
    }

    @GetMapping("/bills")
    public ResponseEntity<?> getBills(@RequestHeader(name = "Authorization", required = false) String auth) {
        String username = null;
        if (auth != null && auth.startsWith("Bearer ")) {
            username = tokenService.getUsernameForToken(auth.substring(7));
        }
        if (username == null) return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));
        List<Bill> bills = billingService.getBillsForUser(username);
        return ResponseEntity.ok(bills);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> payBill(@RequestHeader(name = "Authorization", required = false) String auth,
                                     @RequestBody Map<String, Object> body) {
        String username = null;
        if (auth != null && auth.startsWith("Bearer ")) {
            username = tokenService.getUsernameForToken(auth.substring(7));
        }
        if (username == null) return ResponseEntity.status(401).body(Map.of("error", "unauthorized"));
        Number idN = (Number) body.get("billId");
        if (idN == null) return ResponseEntity.badRequest().body(Map.of("error", "missing_billId"));
        boolean ok = billingService.payBill(username, idN.longValue());
        return ResponseEntity.ok(Map.of("success", ok));
    }
}
