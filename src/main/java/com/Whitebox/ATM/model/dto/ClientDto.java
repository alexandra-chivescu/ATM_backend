package com.Whitebox.ATM.model.dto;
import com.Whitebox.ATM.model.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDto {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public List<AccountDto> accounts;
    public String token;

    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts()
                .stream()
                .map(AccountDto::new)
                .collect(Collectors.toList());
    }

    public ClientDto(Client client, String token) {
        this(client);
        this.token = token;
    }
}
