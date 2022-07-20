package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;

    public void save(String name, User holder, Bank bank) {
        Account account = new Account();
        account.setAccountType(name);
        account.setHolder(holder);
        account.setId(bank.generateID());
        accountDao.save(account);
    }

    public List<Account> getListAccounts() {
        return accountDao.findAll();
    }
}
