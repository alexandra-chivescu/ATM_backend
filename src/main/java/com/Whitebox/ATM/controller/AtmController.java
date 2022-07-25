package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.User;
import com.Whitebox.ATM.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Scanner;

@Controller
public class AtmController {

    @Autowired
    AtmService atmService;

    @Autowired
    AtmDao atmDao;

    @GetMapping("/saveNewAtm")
    public void saveNewUser(@RequestParam("location") String location) {
        atmService.save(location);
    }

    //TODO - make some changes
    @GetMapping("/atm/withdraw/")
    public void showWithdrawal(@RequestParam User user, Scanner scanner) {
        atmService.withdraw(user, scanner);
    }

    //TODO - make some changes
    @GetMapping("/atm/deposit")
    public void showDeposit(@RequestParam User user, Scanner scanner) {
        atmService.deposit(user, scanner);
    }

    //TODO - make some changes
    @GetMapping("/atm/transfer")
    public void showTransfer(@RequestParam User user, Scanner scanner) {
        atmService.transfer(user, scanner);
    }

}
