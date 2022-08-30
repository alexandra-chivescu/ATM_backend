package com.Whitebox.ATM.model;

import com.Whitebox.ATM.Exceptions.WrongAlgorithmForHashingPinException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    private byte secretPin[];
    @Column(
            name = "cvv",
            nullable = false
    )
    @NotBlank(message = "The cvv is required.")
    @Size(min=3, max=3, message = "The cvv must contain 3 digits.")
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

    public int getId() {
        return id;
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
            try {
                throw new WrongAlgorithmForHashingPinException("The algorithm does not exist. ");
            } catch (WrongAlgorithmForHashingPinException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isValidPin(String pin)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            try {
                throw new WrongAlgorithmForHashingPinException("The algorithm does not exist.");
            } catch (WrongAlgorithmForHashingPinException ex) {
                ex.printStackTrace();
            }
        }
        return MessageDigest.isEqual(md.digest(pin.getBytes()), this.secretPin);
    }

    public String cardExpirationDateGenerator() {
        Calendar dateOfToday = Calendar.getInstance();
        dateOfToday.add(Calendar.YEAR, 3);
        Date nextYear = dateOfToday.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
        String nextYearDate = dateFormat.format(nextYear);
        return nextYearDate;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public byte[] getSecretPin() {
        return secretPin;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public Account getAccount() {
        return account;
    }
}
