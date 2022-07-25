package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {
    List<Account> findAll();
}