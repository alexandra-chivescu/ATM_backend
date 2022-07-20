package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name="creditCard")
public class CreditCard {
    @Id
    @Column(name = "card_number")
    private String cardNumber;
    private byte secretPin[];

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setSecretPin(byte[] secretPin) {
        this.secretPin = secretPin;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    private Account account;

    //TODO
    public CreditCard(Account account, String pin, Bank bank) {
        this.cardNumber = bank.getCreditCardNumber();
        this.account = account;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            this.secretPin = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception NoSuchAlgorithmException was found.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public CreditCard() {

    }

    public String getCardNumber() {
        return cardNumber;
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
