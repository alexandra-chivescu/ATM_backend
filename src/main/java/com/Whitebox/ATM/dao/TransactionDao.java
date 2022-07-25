package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAll();
}