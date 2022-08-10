package com.Whitebox.ATM.model.dto;

import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Client;

import java.util.List;

public class ClientDto {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public List<Account> accounts;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts();
    }
}
