package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {

    CreditCard findCreditCardByCardNumber(String cardNumber);
}
