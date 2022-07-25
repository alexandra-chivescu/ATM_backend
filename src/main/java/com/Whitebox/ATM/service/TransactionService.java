package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.TransactionDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionDao transactionDao;

    public void save(double amount, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transactionDao.save(transaction);
    }

    public List<Transaction> getListTransactions() {

        return transactionDao.findAll();
    }
}
