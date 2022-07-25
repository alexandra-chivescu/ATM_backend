package com.Whitebox.ATM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity(name="bank")
@Table(name="bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bank {
    @Id
    @SequenceGenerator(
            name = "bank_sequence",
            sequenceName = "bank_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "bank_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "name",
            nullable = false,
            unique = true
    )
    private String name;
    @OneToMany
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private List<User> users;
    @OneToMany
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id"
    )
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

        Account newAccount = new Account(AccountType.SAVINGS, newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
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
