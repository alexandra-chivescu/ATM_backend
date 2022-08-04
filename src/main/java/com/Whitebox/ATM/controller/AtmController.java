package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.model.dto.AtmDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import com.Whitebox.ATM.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atms")
public class AtmController {

    @Autowired
    AtmService atmService;

    @Autowired
    AtmDao atmDao;

    @PostMapping ("/")
    public String addAtm(@RequestBody AtmDto atmDto) {
        atmService.save(atmDto.location);
        return "New Atm successfully created.";
    }

    @GetMapping("/balance")
    public double getAccountBalance(@RequestBody ClientDepositDto clientDepositDto) {
        return atmService.getBalance(clientDepositDto.accountId);
    }

    @PatchMapping("/withdraw/{amountToWithdraw}")
    public void withdraw(@RequestBody ClientDepositDto clientDepositDto, @PathVariable double amountToWithdraw) {
        atmService.withdraw(clientDepositDto.accountId, amountToWithdraw);
    }

    @PatchMapping("/deposit")
    public void deposit(@RequestBody ClientDepositDto clientDepositDto) {
        atmService.deposit(clientDepositDto.accountId, clientDepositDto.amount);
    }

    @PatchMapping("/transfer/{toAccount}/{amount}")
    public void transfer(@RequestBody ClientDepositDto clientDepositDto, @PathVariable int toAccount, @PathVariable double amount) {
        atmService.transfer(clientDepositDto.accountId, toAccount, amount);
    }

}
