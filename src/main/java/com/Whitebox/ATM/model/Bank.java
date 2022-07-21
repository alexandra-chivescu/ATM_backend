package com.Whitebox.ATM.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name="bank")
public class Bank {
    @Id
    private int id;
    private String name;
    @OneToMany
    private List<User> users;
    @OneToMany
    private List<Account> accounts;
    @OneToMany
    private List<CreditCard> creditCards;

    public Bank(String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
        this.creditCards = new ArrayList<CreditCard>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bank() {
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
        this.creditCards = new ArrayList<CreditCard>();
    }

    public int generateID() {
        int id = 0;
        boolean notUniqueID;
        do {
            notUniqueID = false;
            for (User user : this.users) {
                if(id == user.getId()) {
                    notUniqueID = true;
                    break;
                }
            }
            id ++;

        } while (notUniqueID);
        return id;
    }

    private int getCheckDigit(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }

    public String getCreditCardNumber() {
        Random random = new Random(System.currentTimeMillis());
        int length = 16;
        String bin = "3455";
        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = random.nextInt(10);
            builder.append(digit);
        }

        //Luhn algorithm to generate the check digit.
        int checkDigit = this.getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
    }

    public void addAccount(Account account) {

        this.accounts.add(account);
    }

    public void addCreditCard(CreditCard creditCard) {
        this.creditCards.add(creditCard);
    }


    public User addUser(String firstName, String lastName, String email) {
        User newUser = new User(firstName, lastName, email, this);
        this.users.add(newUser);

        Account newAccount = new Account(String.valueOf(AccountType.SAVINGS), newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public String getName() {
        return name;
    }

    public User userAcceptCard(int idUser, String pin) {
        for(User user: this.users) {
            for(CreditCard creditCard : this.creditCards) {
                if (user.getId() == idUser && creditCard.isValidPin(pin))
                    return user;
            }
        }
        return null;
    }
}
