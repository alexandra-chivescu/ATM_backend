package com.Whitebox.ATM.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AtmDto {
    @JsonProperty("id")
    public int id;
    @JsonProperty("location")
    public String location;
}
