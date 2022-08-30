package com.Whitebox.ATM.model.dto;

import com.Whitebox.ATM.model.ATM;
import com.Whitebox.ATM.model.BanknoteFund;

import java.util.List;

public class AtmDto {
    public int id;
    public String location;
    public List<BanknoteFund> banknoteFunds;

    public AtmDto() {
    }

    public AtmDto(int id, String location, List<BanknoteFund> banknoteFunds) {
        this.id = id;
        this.location = location;
        this.banknoteFunds = banknoteFunds;
    }

    public AtmDto(ATM atm) {
        this.id = atm.getId();
        this.banknoteFunds = atm.getBanknoteFunds();
        this.location = atm.getLocation();
    }

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
