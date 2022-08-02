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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/")
@ResponseBody
public class BankController {

    @Autowired
    ClientDao clientDao;

    @Autowired
    ClientService clientService;

    @Autowired
    BankDao bankDao;

    @Autowired
    BankService bankService;

    @GetMapping("/list/clients")
    public List<ClientDto> showClients() {
        return clientService.getListClients()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}")
    public Client showClientById(@PathVariable int id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/findClientsByEmail/{email}")
    public Client findClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmailAddress(email);
    }

    @PutMapping("/updateClientsNameByEmail")
    public int updateClientNameByEmail(@RequestBody ClientDepositDto clientDepositDto) {
        return clientService.updateClientNameByEmailAddress(clientDepositDto.firstName, clientDepositDto.email);
    }

    @PostMapping("/new/bank")
    public String saveNewBank(@RequestBody BankDto bankDto) {
        bankService.save(bankDto.id, bankDto.name);
        return "New bank successfully created.";
    }

    @PostMapping("/new/client")
    public String saveNewClient(@RequestBody ClientDepositDto clientDepositDto) {
        bankService.addUser(clientDepositDto.clientId, clientDepositDto.firstName, clientDepositDto.lastName, clientDepositDto.email, clientDepositDto.bankId, clientDepositDto.pin, clientDepositDto.cvv);
        return "New client successfully created and added to the bank with id = " + clientDepositDto.bankId;
    }

    @PostMapping("/new/account/{accountType}")
    public String addNewAccount(@RequestBody ClientDepositDto clientDepositDto,  @PathVariable String accountType) {
        clientService.addAccount(clientDepositDto.clientId, clientDepositDto.bankId, clientDepositDto.pin, clientDepositDto.cvv, accountType);
        return "New account successfully created.";
    }


}
