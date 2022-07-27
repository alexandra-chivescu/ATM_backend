package com.Whitebox.ATM.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "clients")
@Table(
        name="clients",
        uniqueConstraints = {
                @UniqueConstraint(name = "client_email_unique", columnNames = "email")
        }
)
public class Client {
    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "client_sequence"
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
    @JsonProperty("first_name")
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
    private List<Account> accounts;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accounts = new ArrayList<Account>();
    }

    public Client() {

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

}
