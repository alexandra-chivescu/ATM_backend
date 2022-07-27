package com.Whitebox.ATM.controller;
import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.model.Client;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import com.Whitebox.ATM.model.dto.ClientDto;
import com.Whitebox.ATM.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/")
@ResponseBody
public class ClientController {

    @Autowired
    ClientDao clientDao;

    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
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

    @GetMapping("/updateClienstNameByEmail/{email}/{first_name}")
    public int updateClientNameByEmail(@PathVariable String first_name, @PathVariable String email) {
        return clientService.updateClientNameByEmailAddress(first_name, email);
    }

   /* @GetMapping("/clients/{clientId}/creditCard")
    public CreditCard showCreditCardByClientId(@PathVariable int clientId) {
        return clientService.getUserCreditCard(clientId);
    }
    */

    @PostMapping("/createClient")
    public String saveNewClient(@RequestBody ClientDepositDto clientDepositDto) {
        clientService.save(clientDepositDto.firstName, clientDepositDto.lastName, clientDepositDto.email);
        return "New client successfully created.";
    }


}
