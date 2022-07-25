package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.TransactionDao;
import com.Whitebox.ATM.dao.UserDao;
import com.Whitebox.ATM.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void save(String firstName, String lastName, String email, Bank bank) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setId(bank.generateID());

        System.out.println("User:" + lastName + " " + firstName  + "-> ID: " + bank.generateID() + "\n");
        userDao.save(user);
    }

    public List<User> getListUsers() {
        return userDao.findAll();
    }

    public User getUserById(int id) {
        return userDao.findById(id);
    }

    public CreditCard getUserCreditCard(int userId) {
        return userDao.findCreditCards(userId);
    }
}
