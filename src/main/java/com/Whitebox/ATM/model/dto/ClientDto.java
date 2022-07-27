package com.Whitebox.ATM.model.dto;

import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Client;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ClientDto {
    @JsonProperty("id")
    public int id;
    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String lastName;
    @JsonProperty("email")
    public String email;
    @JsonProperty("accounts")
    public List<Account> accounts;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts();
    }
}
