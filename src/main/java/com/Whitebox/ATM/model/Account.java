package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "account_type")
    private String AccountType;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User holder;
    @OneToMany
    private List<Transaction> transactions;
    @OneToMany
    private List<CreditCard> creditCards;

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    //TODO must erase this constructor and use AccountService
    public Account(String name, User holder, Bank bank) {
        this.AccountType = name;
        this.holder = holder;
        this.id = bank.generateID();

        this.transactions = new ArrayList<Transaction>();
        this.creditCards = new ArrayList<CreditCard>();
    }
    public Account() {
        this.transactions = new ArrayList<Transaction>();
        this.creditCards = new ArrayList<CreditCard>();
    }

    public int getId() {
        return id;
    }

    public String getInfoLine() {
        double balance = this.getBalance();
        //Write for each currency
        return String.format("%s : lei %.02f : %s", this.id, balance, this.AccountType);
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction transaction : this.transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    public void addCreditCard(CreditCard creditCard) {
        this.creditCards.add(creditCard);
    }

    public void addTransaction(double amount) {
        Transaction transaction = new Transaction(amount, this);
        this.transactions.add(transaction);
    }



}
