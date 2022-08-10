package com.Whitebox.ATM.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="administrator")
@Table(name="administrator")
public class Administrator {
    @Id
    private String username;
    private String password;
}
