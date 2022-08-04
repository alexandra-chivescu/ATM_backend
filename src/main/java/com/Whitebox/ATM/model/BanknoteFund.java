package com.Whitebox.ATM.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="banknote_funds")
@Table(name="banknote_funds")
public class BanknoteFund {
    @Id
    private Banknote banknote;

    private int amount;

    public BanknoteFund(Banknote banknote, int amount) {
        this.banknote = banknote;
        this.amount = amount;
    }

    public BanknoteFund() {

    }
}
