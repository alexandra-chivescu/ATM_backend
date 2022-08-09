package com.Whitebox.ATM.model;

import com.Whitebox.ATM.model.dto.AtmDto;

import javax.persistence.*;

@Entity(name="banknote_funds")
@Table(name="banknote_funds")
public class BanknoteFund {
    @Id
    @SequenceGenerator(
            name = "banknote_fund_sequence",
            sequenceName = "banknote_fund_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "banknote_fund_sequence"
    )
    @Column(
            name = "id",
            updatable = false,
            nullable = true
    )
    private int id;
    @Column(name = "banknote")
    private Banknote banknote;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    private ATM atm;

    public void setAtm(ATM atm) {
        this.atm = atm;
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public int getAmount() {
        return amount;
    }

    public BanknoteFund() {

    }

    public BanknoteFund(Banknote banknote, int amount, ATM atm) {
        this.banknote = banknote;
        this.amount = amount;
        this.atm = atm;
    }
}
