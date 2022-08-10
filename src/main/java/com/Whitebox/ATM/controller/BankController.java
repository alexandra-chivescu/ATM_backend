package com.Whitebox.ATM.controller;
import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.model.Client;
import com.Whitebox.ATM.model.dto.BankDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import com.Whitebox.ATM.model.dto.ClientDto;
import com.Whitebox.ATM.service.BankService;
import com.Whitebox.ATM.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class BankController {

    @Autowired
    ClientDao clientDao;

    @Autowired
    ClientService clientService;

    @Autowired
    BankDao bankDao;

    @Autowired
    BankService bankService;

    @GetMapping("/clients")
    public List<ClientDto> getClients() {
        return clientService.getListClients()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
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

    @PostMapping("/clients")
    public String addClient(@RequestBody ClientDepositDto clientDepositDto) {
        bankService.addClient(clientDepositDto.clientId, clientDepositDto.firstName, clientDepositDto.lastName, clientDepositDto.email, clientDepositDto.bankId, clientDepositDto.pin, clientDepositDto.cvv);
        return "New client successfully created and added to the bank with id = " + clientDepositDto.bankId;
    }

    @PostMapping("/accounts/{accountType}")
    public String addAccount(@RequestBody ClientDepositDto clientDepositDto,  @PathVariable String accountType) {
        clientService.addAccount(clientDepositDto.clientId, clientDepositDto.bankId, clientDepositDto.pin, clientDepositDto.cvv, accountType);
        return "New account successfully created.";
    }


}
