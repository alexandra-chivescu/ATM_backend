package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity(name = "credit_cards")
@Table(
        name="credit_cards",
        uniqueConstraints = {
                @UniqueConstraint(name = "card_number_unique", columnNames = "card_number")
        })

public class CreditCard {
    @Id
    @Column(
            name = "card_number",
            nullable = false
    )
    private String cardNumber;
    @Column(
            name = "pin",
            nullable = false,
            unique = true
    )
    private byte secretPin[];
    @Column(
            name = "cvv",
            nullable = false
    )
    private String cvv;
    @Column(
            name = "expiration_date",
            nullable = false
    )
    private String expirationDate;

    @ManyToOne
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id"
    )
    private Account account;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setSecretPin(byte[] secretPin) {
        this.secretPin = secretPin;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CreditCard() {

    }

    public CreditCard(Account account, Bank bank, String pin, String cvv, String expireDate) {
        this.cardNumber = bank.getCreditCardNumber();
        this.account = account;
        this.cvv = cvv;
        this.expirationDate = expireDate;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            this.secretPin = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception NoSuchAlgorithmException was found.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isValidPin(String pin) {
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


}
