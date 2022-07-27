package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.model.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    BankDao bankDao;

    public void save(String name) {
        Bank bank = new Bank(name);
        bank.setName(name);
        bankDao.save(bank);
    }

    public List<Bank> getListBanks() {
        return bankDao.findAll();
    }
}
