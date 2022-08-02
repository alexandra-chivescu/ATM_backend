package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.AccountType;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    BankDao bankDao;

    public void save(int bankId, String name) {
        Bank bank = new Bank(bankId, name);
        bank.setId(bankId);
        bank.setName(name);
        bankDao.save(bank);
    }

    public void addUser(int clientId, String firstName, String lastName, String email, int bankId, String pin, String cvv) {
        Bank bank = bankDao.findById(bankId).get();
        bank.addUser(clientId, firstName, lastName, email, pin, cvv);
        bankDao.save(bank);
    }

    public List<Bank> getListBanks() {
        return bankDao.findAll();
    }
}
