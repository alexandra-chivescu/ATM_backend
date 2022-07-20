package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.TransactionDao;
import com.Whitebox.ATM.dao.UserDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.Transaction;
import com.Whitebox.ATM.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void save(String firstName, String lastName, Bank bank) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setId(bank.generateID());

        System.out.println("User:" + lastName + " " + firstName  + "-> ID: " + bank.generateID() + "\n");
        userDao.save(user);
    }

    public List<User> getListUsers() {
        return userDao.findAll();
    }

}
