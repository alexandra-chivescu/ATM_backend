package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    List<User> findAll();

    User findById(int id);


    @Query(
            value= "select * from credit_card c inner join ",
            nativeQuery = true
    )
    CreditCard findCreditCards(int userId);
}