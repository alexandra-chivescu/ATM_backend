package com.Whitebox.ATM;

import com.Whitebox.ATM.dao.UserDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.AccountType;
import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.User;
import com.Whitebox.ATM.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Test
    public void saveUser() {
        CreditCard creditCard = CreditCard.builder()
                .cvv("345")
                .expirationDate("29-12-2023")
                .build();

        Account account = Account.builder()
                .accountType(AccountType.CHECKING)
                .creditCards(List.of(creditCard))
                .build();

        User user =
                User.builder()
                        .email("alechivescu@gmail.com")
                        .firstName("Alex")
                        .lastName("Chivescu")
                        .accounts(List.of(account))
                        .build();

    }

    @Test
    public void printUsers() {
        List<User> users =
                userDao.findAll();

        System.out.println("users = " + users);
    }
}
