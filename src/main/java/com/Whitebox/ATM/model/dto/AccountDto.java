package com.Whitebox.ATM.model.dto;

import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.AccountType;

public class AccountDto {
    public int id;
    public AccountType accountType;
    public double balance;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.accountType = account.getAccountType();
        this.balance = account.getBalance();
    }
}
