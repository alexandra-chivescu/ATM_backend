package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankDao extends JpaRepository<Bank, Integer> {

    List<Bank> findAll();
}
