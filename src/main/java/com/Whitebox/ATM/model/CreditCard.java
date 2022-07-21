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
    private String cvv;
    private String expireDate;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setSecretPin(byte[] secretPin) {
        this.secretPin = secretPin;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    @ManyToOne
    private Account account;

    //TODO
    public CreditCard(Account account, Bank bank, String pin, String cvv, String expireDate) {
        this.cardNumber = bank.getCreditCardNumber();
        this.account = account;
        this.cvv = cvv;
        this.expireDate = expireDate;

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
