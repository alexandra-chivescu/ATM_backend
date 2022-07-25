package com.Whitebox.ATM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="account")
@Table(name="account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(name = "account_type")
    private AccountType accountType;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User holder;
    @OneToMany
    @JoinColumn(
            name = "transaction_id",
            referencedColumnName = "id"
    )
    private List<Transaction> transactions;
    @OneToMany
    private List<CreditCard> creditCards;

    public Account(AccountType name, User holder, Bank bank) {
        this.accountType = name;
        this.holder = holder;
        this.id = bank.generateID();

        this.transactions = new ArrayList<Transaction>();
        this.creditCards = new ArrayList<CreditCard>();
    }

    public String getInfoLine() {
        double balance = this.getBalance();
        //Write for each currency
        return String.format("%s : lei %.02f : %s", this.id, balance, this.accountType);
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
