package com.Whitebox.ATM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Table(
        name="\"user\"",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id"
    )
    private List<Account> accounts;

    public User(String firstName, String lastName, String email, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = bank.generateID();

        //create a new list of accounts for the new user
        this.accounts = new ArrayList<Account>();

        System.out.println("User:" + lastName + " " + firstName  + "-> ID: " + this.id + "\n");
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
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
