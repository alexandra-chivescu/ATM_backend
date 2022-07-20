package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDao extends CrudRepository<CreditCard, Integer> {
    List<CreditCard> findAll();
}
