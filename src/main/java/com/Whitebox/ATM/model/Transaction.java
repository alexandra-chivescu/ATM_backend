package com.Whitebox.ATM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name="transaction")
@Table(name="transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "amount",
            nullable = false
    )
    private double amount;
    private Date date;
    @ManyToOne
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "id"
    )
    private Account account;

    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.account = account;
        this.date = new Date();
    }
}
