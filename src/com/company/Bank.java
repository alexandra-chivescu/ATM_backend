package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getUserID() {
        String id = "";
        boolean notUniqueID;
        Random rnd = new Random();
        do {
            //5 digit id
            id = String.valueOf(rnd.nextInt(99999));
            notUniqueID = false;
            for (User user : this.users) {
                if(id.compareTo(user.getId()) == 0 ) {
                    notUniqueID = true;
                    break;
                }
            }

        } while (notUniqueID);
        return id;
    }

    public String getAccountID() {
        String id = "";
        boolean notUniqueID;
        Random rnd = new Random();
        do {
            //8 digit id because the user can have multiple accounts
            id = String.valueOf(rnd.nextInt(99999999));
            notUniqueID = false;
            for (Account account : this.accounts) {
                if(id.compareTo(account.getId()) == 0 ) {
                    notUniqueID = true;
                    break;
                }
            }

        } while (notUniqueID);
        return id;
    }

    public void addAccount(Account account) {

        this.accounts.add(account);
    }

    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account(String.valueOf(TypesAccounts.Savings), newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userAcceptCard(String idUser, String pin) {
        for(User user: this.users) {
            if(user.getId().compareTo(idUser) == 0 && user.validPin(pin))
                return user;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
