package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankDao extends CrudRepository<Bank, Integer> {

    List<Bank> findAll();
}
