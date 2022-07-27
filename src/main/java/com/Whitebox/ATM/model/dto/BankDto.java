package com.Whitebox.ATM.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankDto {
    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;
}
