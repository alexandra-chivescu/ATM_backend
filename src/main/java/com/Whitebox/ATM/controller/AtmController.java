package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.model.ATM;
import com.Whitebox.ATM.model.BanknoteFund;
import com.Whitebox.ATM.model.dto.*;
import com.Whitebox.ATM.service.AtmService;
import com.Whitebox.ATM.service.BanknoteFundService;
import com.Whitebox.ATM.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<ATM> addAtm(@RequestBody AtmDto atmDto) {
        return ResponseEntity.ok(atmService.save(atmDto.location, atmDto.banknoteFunds));
    }

    @PatchMapping("/funds")
    public ResponseEntity<List<BanknoteFund>> addFunds(@RequestBody AtmDto atmDto) {
        return ResponseEntity.ok(banknoteFundService.addFunds(atmDto.banknoteFunds, atmDto.id));
    }

    @GetMapping
    public ResponseEntity<List<AtmDto>> getAtms() {
        return new ResponseEntity<>(atmService.getAtms()
                .stream()
                .map(AtmDto::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/clients/login")
    public ResponseEntity<ClientDto> loginClient(@RequestBody CreditCardDto creditCardDto) {
        return ResponseEntity.ok(creditCardService.verifyCardDetails(creditCardDto.cardNumber, creditCardDto.cvv, creditCardDto.pin));
    }

    @PostMapping("/clients/logout")
    public void logout(@RequestBody ClientDepositDto clientDepositDto) {
        atmService.logout(clientDepositDto.clientId);
    }


    @GetMapping("/balance/{id}")
    public double getAccountBalance(@PathVariable int id) {
        return atmService.getBalance(id);
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<WithdrawDto> withdraw(@RequestBody ClientDepositDto clientDepositDto) {
        return ResponseEntity.ok(new WithdrawDto(atmService.withdraw(clientDepositDto.clientId, clientDepositDto.amountToWithdraw, clientDepositDto.token)));
    }

    @PatchMapping("/deposit")
    public void deposit(@RequestBody ClientDepositDto clientDepositDto) {
        atmService.deposit(clientDepositDto.clientId, clientDepositDto.amount);
    }

    @PatchMapping("/transfer")
    public void transfer(@RequestBody ClientDepositDto clientDepositDto) {
        atmService.transfer(clientDepositDto.accountId, clientDepositDto.toAccount, clientDepositDto.amount);
    }

}
