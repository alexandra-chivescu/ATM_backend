package com.company;

import java.util.ArrayList;

enum TypesAccounts {
    Checking,
    Savings,
    MoneyMarket,
    certificateOfDeposit
}

public class Account {

    private String id;
    private String name;
    //private double balance;
    private User holder;
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank bank) {
        this.name = name;
        this.holder = holder;
        this.id = bank.getAccountID();
        this.transactions = new ArrayList<Transaction>();
    }

    public String getId() {
        return id;
    }

    public String getInfoLine() {
        double balance = this.getBalance();
        //Write for each currency
        return String.format("%s : lei %.02f : %s", this.id, balance, this.name);
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction transaction : this.transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public void addTransaction(double amount) {
         Transaction transaction = new Transaction(amount, this);
         this.transactions.add(transaction);
    }
}
