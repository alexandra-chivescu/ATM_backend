package com.Whitebox.ATM.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    public void setLocation(String location) {
        this.location = location;
    }
}
