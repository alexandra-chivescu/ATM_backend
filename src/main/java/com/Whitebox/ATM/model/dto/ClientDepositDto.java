package com.Whitebox.ATM.model.dto;

public class ClientDepositDto {
    public int atmId;
    public int clientId;
    public double amountToWithdraw;
    public int toAccount;
    public String firstName;
    public String lastName;
    public String email;
    public double amount;
    public int accountId;
    public int bankId;
    public String pin;
    public String cvv;
    public String token;
    public String bankName;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public void setAmountToWithdraw(double amountToWithdraw) {
        this.amountToWithdraw = amountToWithdraw;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
