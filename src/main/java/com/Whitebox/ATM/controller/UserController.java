package com.Whitebox.ATM.controller;
import com.Whitebox.ATM.dao.UserDao;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.User;
import com.Whitebox.ATM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> showUsers() {
        return userService.getListUsers();
    }

    @GetMapping("/users/{id}")
    public User showUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/{userId}/creditCard")
    public CreditCard showCreditCardByUserId(@PathVariable int userId) {
        return userService.getUserCreditCard(userId);
    }

    @GetMapping("/saveNewUser")
    public void saveNewUser(@RequestParam("first_name") String firstName,
                                  @RequestParam("last_name") String lastName,
                                  @RequestParam("email") String email,
                                  @RequestParam("bank") Bank bank) {
        userService.save(firstName, lastName, email, bank);
    }

}
