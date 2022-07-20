package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private double amount;
    private Date date;
    @ManyToOne
    private Account account;

    //TODO
    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.account = account;
        this.date = new Date();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Transaction() {
        this.date = new Date();
    }

    public double getAmount() {
        return amount;
    }

}
