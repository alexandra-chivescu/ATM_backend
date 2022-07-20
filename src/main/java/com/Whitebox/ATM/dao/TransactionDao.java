package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao extends CrudRepository<Transaction, Integer> {
    List<Transaction> findAll();
}