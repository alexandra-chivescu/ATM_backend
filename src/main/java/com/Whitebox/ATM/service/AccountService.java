package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.ResourceNotFoundException;
import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.AccountType;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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
        accountDao.save(account);
    }

    public List<Account> getListAccounts() {
        return accountDao.findAll();
    }

    public void addTransaction(int accountId, double amount) {
        Account account = accountDao.findById(accountId).get();
        if(account == null) {
            throw new ResourceNotFoundException("Account with id " + accountId + "not found.");
        }
        account.addTransaction(amount);
        accountDao.save(account);
    }

    public double getBalance(int clientId) {
        List<Account> accounts = accountDao.findAllByHolder_Id(clientId);
        return accounts
                .stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }



}
