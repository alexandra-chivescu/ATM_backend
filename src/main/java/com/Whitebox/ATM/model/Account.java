package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="accounts")
@Table(name="accounts")
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
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    private Client holder;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<Transaction> transactions;
    @OneToMany
    private List<CreditCard> creditCards;

    public Account(AccountType name, Client holder, Bank bank) {
        this.accountType = name;
        this.holder = holder;
        this.id = bank.generateID();

        this.transactions = new ArrayList<Transaction>();
        this.creditCards = new ArrayList<CreditCard>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setHolder(Client holder) {
        this.holder = holder;
    }

    public int getId() {
        return id;
    }

    public Account() {

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
