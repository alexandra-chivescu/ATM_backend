package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
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

    public void addClient(ClientDepositDto clientDepositDto) {
        Bank bank = bankDao.findBankByName(clientDepositDto.bankName);
        bank.addClient(clientDepositDto.clientId, clientDepositDto.firstName, clientDepositDto.lastName, clientDepositDto.email, clientDepositDto.pin, clientDepositDto.cvv);
        bankDao.save(bank);
    }

}
