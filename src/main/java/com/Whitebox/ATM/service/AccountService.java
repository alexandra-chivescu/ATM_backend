package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.AccountType;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;

    public void save(AccountType accountType, Client holder, Bank bank) {
        Account account = new Account(accountType, holder, bank);
        account.setAccountType(accountType);
        account.setHolder(holder);
        account.setId(bank.generateID());
        accountDao.save(account);
    }

    public List<Account> getListAccounts() {
        return accountDao.findAll();
    }

    public void addTransaction(int accountId, double amount) {
        Account account = accountDao.findById(accountId).get();
        account.addTransaction(amount);
        accountDao.save(account);
    }

    public double getBalance(int accountId) {
        Account account = accountDao.findById(accountId).get();
        return account.getBalance();
    }



}
