package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findAll();
}
