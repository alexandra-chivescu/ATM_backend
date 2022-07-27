package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.Client;
import com.Whitebox.ATM.model.dto.ClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {

    Client findById(int id);

   /* @Query(
            //TODO
            value= "",
            nativeQuery = true
    )
    CreditCard findCreditCards(int userId); */

    @Query("select c from clients c where c.email = :email")
    Client findUserByEmailAddress(@Param("email") String email);

    @Modifying
    @Query(
            value = "update clients set first_name = :first_name where email = :email",
            nativeQuery = true
    )
    int updateUserNameByEmailAddress(@Param("first_name") String first_name, @Param("email") String email);
}