package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Banknote;
import com.Whitebox.ATM.model.BanknoteFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BanknoteFundDao extends JpaRepository<BanknoteFund, Integer> {
    @Transactional
    @Modifying
    @Query(
            value = "update banknote_funds" +
                    " set amount = amount + :amount" +
                    " where banknote = :banknote and atm_id = :atm_id",
            nativeQuery = true
    )
    void updateAmount(@Param("banknote") int banknote, @Param("amount") int amount, @Param("atm_id") int atm_id);

    @Transactional
    @Modifying
    @Query(
            value = "select amount" +
                    " from banknote_funds" +
                    " where atm_id = :atmId",
            nativeQuery = true
    )
    int[] selectAmount(@Param("atmId") int atmId);

    @Transactional
    @Modifying
    @Query(
            value = "update banknote_funds" +
                    " set amount = :amount" +
                    " where banknote = :banknote and atm_id = :atmId",
            nativeQuery = true
    )
    void updateAmountAfterWithdraw(@Param("banknote") int banknote, @Param("amount") int amount, @Param("atmId") int atmId);
}
