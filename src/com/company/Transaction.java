package com.company;

import java.util.Date;

public class Transaction {

    private double amount;
    private Date date;
    private Account account;

    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.account = account;
        this.date = new Date();
    }

    public double getAmount() {
        return amount;
    }
}
