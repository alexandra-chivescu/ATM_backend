package com.Whitebox.ATM.model.dto;

import com.Whitebox.ATM.model.BanknoteFund;

import java.util.List;

public class AtmDto {
    public int id;
    public String location;
    public List<BanknoteFund> banknoteFunds;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBanknoteFunds(List<BanknoteFund> banknoteFunds) {
        this.banknoteFunds = banknoteFunds;
    }

    public void setId(int id) {
        this.id = id;
    }
}
