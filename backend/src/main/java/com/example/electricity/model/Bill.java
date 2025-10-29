package com.example.electricity.model;

public class Bill {
    private long id;
    private String username;
    private String month;
    private double amount;
    private boolean paid;

    public Bill() {}

    public Bill(long id, String username, String month, double amount, boolean paid) {
        this.id = id;
        this.username = username;
        this.month = month;
        this.amount = amount;
        this.paid = paid;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}
