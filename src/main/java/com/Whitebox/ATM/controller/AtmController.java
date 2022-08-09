package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.model.ATM;
import com.Whitebox.ATM.model.BanknoteFund;
import com.Whitebox.ATM.model.dto.AtmDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import com.Whitebox.ATM.service.AtmService;
import com.Whitebox.ATM.service.BanknoteFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atms")
public class AtmController {

    @Autowired
    AtmService atmService;

    @Autowired
    AccountDao accountDao;

    @Autowired
    AtmDao atmDao;

    @Autowired
    BanknoteFundService banknoteFundService;

    @PostMapping ("/")
    public String addAtm(@RequestBody AtmDto atmDto) {
        atmService.save(atmDto.location);
        return "New Atm successfully created.";
    }

    @PostMapping ("/funds")
    public ResponseEntity<ATM> setFunds(@RequestBody AtmDto atmDto) {
        return ResponseEntity.ok(atmService.setFunds(atmDto.id, atmDto.banknoteFunds));
    }

    @PatchMapping("/funds")
    public List<BanknoteFund> addFunds(@RequestBody AtmDto atmDto) {
        return banknoteFundService.addFunds(atmDto.banknoteFunds, atmDto.id);
    }

    @GetMapping("/balance")
    public double getAccountBalance(@RequestBody ClientDepositDto clientDepositDto) {
        return atmService.getBalance(clientDepositDto.accountId);
    }

    @PatchMapping("/withdraw/{amountToWithdraw}")
    public void withdraw(@RequestBody ClientDepositDto clientDepositDto, @PathVariable double amountToWithdraw) {
        atmService.withdraw(clientDepositDto.accountId, amountToWithdraw, clientDepositDto.atmId);
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
