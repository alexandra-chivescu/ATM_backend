package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany
    private List<Account> accounts;

    //TODO
    public User(String firstName, String lastName, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = bank.generateID();

        //create a new list of accounts for the new user
        this.accounts = new ArrayList<Account>();

        System.out.println("User:" + lastName + " " + firstName  + "-> ID: " + this.id + "\n");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User() {
        this.accounts = new ArrayList<Account>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public int getId() {
        return id;
    }

    public void printAccountsInfo() {
        System.out.println(this.firstName + " " + this.lastName + "'s accounts info: \n");
        accounts.forEach(account -> System.out.println(account.getInfoLine()));

    }

    public int numAccounts() {

        return this.accounts.size();
    }

    public double getAccountBalance(int accountIndex) {

        return this.accounts.get(accountIndex).getBalance();
    }

    public String getAccountId(int accountIndex) {

        return String.valueOf(this.accounts.get(accountIndex).getId());
    }

    public void addAccountTransaction(int accountIndex, double amount) {
        this.accounts.get(accountIndex).addTransaction(amount);
    }

}
