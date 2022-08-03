package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.dao.CreditCardDao;
import com.Whitebox.ATM.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientDao clientDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    BankDao bankDao;

    @Autowired
    CreditCardDao creditCardDao;

    public List<Client> getListClients() {
        return clientDao.findAll();
    }

    public Client getClientByEmailAddress(String email) {
        return clientDao.findUserByEmailAddress(email);
    }

    public int updateClientNameByEmailAddress(String first_name ,String last_name, String email) {
        return clientDao.updateUserNameByEmailAddress(first_name,last_name, email);
    }

    public void addAccount(int clientId, int bankId, String pin, String cvv, String accountType) {
        Client client = clientDao.findById(clientId).get();
        Account account = new Account(AccountType.valueOf(accountType), client, bankDao.findById(bankId).get());
        client.addAccount(account);
        accountDao.save(account);

        CreditCard creditCard = new CreditCard(account, bankDao.findById(bankId).get(), pin, cvv);
        account.addCreditCard(creditCard);
        bankDao.findById(bankId).get().addCreditCard(creditCard);
        creditCardDao.save(creditCard);
    }
}
