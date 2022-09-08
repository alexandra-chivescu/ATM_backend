package com.Whitebox.ATM.controller;
import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.model.Administrator;
import com.Whitebox.ATM.model.Client;
import com.Whitebox.ATM.model.dto.*;
import com.Whitebox.ATM.service.AdministratorService;
import com.Whitebox.ATM.service.AtmService;
import com.Whitebox.ATM.service.BankService;
import com.Whitebox.ATM.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class BankController {

    @Autowired
    ClientDao clientDao;

    @Autowired
    ClientService clientService;

    @Autowired
    BankDao bankDao;

    @Autowired
    BankService bankService;

    @Autowired
    AdministratorService administratorService;

    @Autowired
    AtmService atmService;

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getClients() {
        return new ResponseEntity<>(clientService.getListActiveClients()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/clients/{email}")
    public Client getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmailAddress(email);
    }

    @PatchMapping("/clients")
    public void updateClientNameByEmail(@RequestBody ClientDepositDto clientDepositDto) {
        clientService.updateClientNameByEmailAddress(clientDepositDto.firstName, clientDepositDto.lastName, clientDepositDto.email);
    }

    @PostMapping("/banks")
    public String addBank(@RequestBody BankDto bankDto) {
        bankService.save(bankDto.id, bankDto.name);
        return "New bank successfully created.";
    }

    @PostMapping("/")
    public ResponseEntity<Administrator> addAdministrator(@RequestBody AdministratorDto administratorDto) {
        return ResponseEntity.ok(administratorService.save(administratorDto.username, administratorDto.password));
    }

    @PostMapping("/login")
    public ResponseEntity<Administrator> loginAdministrator(@RequestBody AdministratorDto administratorDto) {
        return ResponseEntity.ok(administratorService.verifyAndGetUser(administratorDto.username, administratorDto.password).get());
    }

    @PostMapping("/clients")
    public void addClient(@RequestBody ClientDepositDto clientDepositDto) {
        bankService.addClient(clientDepositDto.clientId, clientDepositDto.firstName, clientDepositDto.lastName, clientDepositDto.email, clientDepositDto.bankName, clientDepositDto.pin, clientDepositDto.cvv);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Client> inactivateClient(@PathVariable int id) {
        clientService.inactivateClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountType}")
    public String addAccount(@RequestBody ClientDepositDto clientDepositDto,  @PathVariable String accountType) {
        clientService.addAccount(clientDepositDto.clientId, clientDepositDto.bankId, clientDepositDto.pin, clientDepositDto.cvv, accountType);
        return "New account successfully created.";
    }

}
