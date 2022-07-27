package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientDao clientDao;


    public void save(String firstName, String lastName, String email) {
        Client client = new Client(firstName, lastName, email);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);

        clientDao.save(client);
    }

    public List<Client> getListClients() {
        return clientDao.findAll();
    }

    public Client getClientById(int id) {
        return clientDao.findById(id);
    }

    /*public CreditCard getUserCreditCard(int userId) {
        return userDao.findCreditCards(userId);
    } */

    public Client getClientByEmailAddress(String email) {
        return clientDao.findUserByEmailAddress(email);
    }

    public int updateClientNameByEmailAddress(String first_name ,String email) {
        return clientDao.updateUserNameByEmailAddress(first_name, email);
    }
}
