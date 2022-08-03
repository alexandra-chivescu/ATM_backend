package com.Whitebox.ATM.model;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity(name="transactions")
@Table(name="transactions")
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "amount",
            nullable = false
    )
    @Positive(message = "The transaction amount must be greater than 0.")
    private double amount;
    private Date date;
    @ManyToOne
    private Account account;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.account = account;
        this.date = new Date();
    }

    public Transaction() {

    }
}
