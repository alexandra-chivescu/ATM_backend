package com.Whitebox.ATM.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity(name="banks")
@Table(name="banks")
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
    @NotBlank(message = "The name of the bank is required.")
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<Client> clients;
    @OneToMany
    private List<Account> accounts;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<CreditCard> creditCards;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank(int id, String name) {
        this.id = id;
        this.name = name;
        this.clients = new ArrayList<Client>();
        this.accounts = new ArrayList<Account>();
        this.creditCards = new ArrayList<CreditCard>();
    }

    public Bank() {

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

    public void addUser(int clientId, String firstName, String lastName, String email, String pin, String cvv) {
        Client client = new Client(clientId, firstName, lastName, email, this);
        this.clients.add(client);

        Account account = new Account(AccountType.SAVINGS, client, this);
        client.addAccount(account);
        this.addAccount(account);

        CreditCard creditCard = new CreditCard(account, this, pin, cvv);
        account.addCreditCard(creditCard);
        this.addCreditCard(creditCard);
    }

    public Client userAcceptCard(int idUser, String pin) {
        for(Client user: this.clients) {
            for(CreditCard creditCard : this.creditCards) {
                if (user.getId() == idUser && creditCard.isValidPin(pin))
                    return user;
            }
        }
        return null;
    }
}
