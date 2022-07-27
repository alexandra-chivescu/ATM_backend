package com.Whitebox.ATM.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardDto {
    @JsonProperty("card_number")
    public String cardNumber;
    @JsonProperty("cvv")
    public String cvv;
    @JsonProperty("expiration_date")
    public String expiration_date;
    @JsonProperty("pin")
    public byte pin[];
    @JsonProperty("account_id")
    public int account_id;
}
