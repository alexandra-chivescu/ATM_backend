package com.Whitebox.ATM.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "credit_cards")
@Table(
        name="credit_cards",
        uniqueConstraints = {
                @UniqueConstraint(name = "card_number_unique", columnNames = "card_number")
        })

public class CreditCard {
    @Id
    @SequenceGenerator(
            name = "credit_card_sequence",
            sequenceName = "credit_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "credit_card_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "card_number",
            nullable = false
    )
    @NotBlank(message = "The card number is required.")
    private String cardNumber;
    @Column(
            name = "pin",
            nullable = false
    )
    @NotBlank(message = "The pin is required.")
    private byte secretPin[];
    @Column(
            name = "cvv",
            nullable = false
    )
    @NotBlank(message = "The cvv is required.")
    private String cvv;
    @Column(
            name = "expiration_date",
            nullable = false
    )
    @NotBlank(message = "The expiration date is required.")
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

    public CreditCard(Account account, Bank bank, String pin, String cvv) {
        this.cardNumber = bank.getCreditCardNumber();
        this.account = account;
        this.cvv = cvv;
        this.expirationDate = cardExpirationDateGenerator();

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

    public String cardExpirationDateGenerator() {
        Calendar dateOfToday = Calendar.getInstance();
        dateOfToday.add(Calendar.YEAR, 3);
        Date nextYear = dateOfToday.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
        String nextYearDate = dateFormat.format(nextYear);
        return nextYearDate;
    }


}
