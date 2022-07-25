package com.Whitebox.ATM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "atm")
@Table(name = "atm")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String location;


}
