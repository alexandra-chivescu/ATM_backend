package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.dto.ClientDto;
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

    public void addClient(int clientId, String firstName, String lastName, String email, String bankName, String pin, String cvv) {
        Bank bank = bankDao.findBankByName(bankName);
        bank.addClient(clientId, firstName, lastName, email, pin, cvv);
        bankDao.save(bank);
    }

    public List<Bank> getListBanks() {
        return bankDao.findAll();
    }
}
