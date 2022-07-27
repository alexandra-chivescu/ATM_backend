package com.Whitebox.ATM.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDepositDto {
    public int clientId;
    public String firstName;
    public String lastName;
    public String email;
    public double amount;
    public int accountId;
}
