package com.Whitebox.ATM.model;

import com.Whitebox.ATM.Exceptions.WrongAlgorithmForHashingPinException;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Entity(name="administrators")
@Table(name="administrators")
public class Administrator {
    @Id
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "administrator_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    private String username;
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String hashPassword(String password)  {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            password = Base64.getEncoder().encodeToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            try {
                throw new WrongAlgorithmForHashingPinException("The algorithm does not exist. ");
            } catch (WrongAlgorithmForHashingPinException ex) {
                ex.printStackTrace();
            }
        }

        return password;
    }

}
