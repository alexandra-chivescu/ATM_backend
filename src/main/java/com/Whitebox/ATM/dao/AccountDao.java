package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDao extends CrudRepository<Account, Integer> {
    List<Account> findAll();
}