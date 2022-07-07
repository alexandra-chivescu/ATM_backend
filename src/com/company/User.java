package com.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private byte secretPin[];
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = bank.getUserID();

        //create a new list of accounts for the new user
        this.accounts = new ArrayList<Account>();

        //SHA-256 algorithm
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            this.secretPin = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Caught error NoSuchAlgorithmException :'(");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.printf("User: %s %s -> ID: %s \n", lastName, firstName, this.id);
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getId() {
        return id;
    }

    public boolean validPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.secretPin);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Caught error NoSuchAlgorithmException :'(");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountsInfo() {
        System.out.printf("%s's accounts info: \n", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d -> %s\n", i+1,  this.accounts.get(i).getInfoLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public double getAccountBalance(int accountIndex) {
        return this.accounts.get(accountIndex).getBalance();
    }

    public String getAccountId(int accountIndex) {
        return this.accounts.get(accountIndex).getId();
    }

    public void addAccountTransaction(int accountIndex, double amount) {
        this.accounts.get(accountIndex).addTransaction(amount);
    }

}
