package com.Whitebox.ATM.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "atms")
@Table(name = "atms")
public class ATM {
    @Id
    @SequenceGenerator(
            name = "atm_sequence",
            sequenceName = "atm_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "atm_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "location",
            nullable = false
    )
    @NotBlank(message = "The location is required.")
    private String location;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<BanknoteFund> banknoteFunds;

    public void setBanknoteFunds(List<BanknoteFund> banknoteFunds) {
        this.banknoteFunds = banknoteFunds;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public List<BanknoteFund> getBanknoteFunds() {
        return banknoteFunds;
    }
}
