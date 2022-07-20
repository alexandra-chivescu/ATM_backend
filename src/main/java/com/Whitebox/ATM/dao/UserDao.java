package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    List<User> findAll();
}