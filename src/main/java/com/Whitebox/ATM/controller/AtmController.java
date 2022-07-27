package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.model.dto.AtmDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import com.Whitebox.ATM.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@ResponseBody
public class AtmController {

    @Autowired
    AtmService atmService;

    @Autowired
    AtmDao atmDao;

    @PostMapping ("/createAtm")
    public String saveNewAtm(@RequestBody AtmDto atmDto) {
        atmService.save(atmDto.location);
        return "New Atm successfully created.";
    }

    @PutMapping("/atm/accountBalance")
    public double showAccountBalance(@RequestBody ClientDepositDto clientDepositDto) {
        return atmService.getBalance(clientDepositDto.accountId);
    }

    @PutMapping("/atm/withdraw/{amountToWithdraw}")
    public void withdraw(@RequestBody ClientDepositDto clientDepositDto, @PathVariable double amountToWithdraw) {
        atmService.withdraw(clientDepositDto.accountId, amountToWithdraw);
    }

    @PutMapping("/atm/deposit")
    public void deposit(@RequestBody ClientDepositDto clientDepositDto) {
        atmService.deposit(clientDepositDto.accountId, clientDepositDto.amount);
    }

    @PutMapping("/atm/transfer/{toAccount}/{amount}")
    public void transfer(@RequestBody ClientDepositDto clientDepositDto, @PathVariable int toAccount, @PathVariable double amount) {
        atmService.transfer(clientDepositDto.accountId, toAccount, amount);
    }

}
