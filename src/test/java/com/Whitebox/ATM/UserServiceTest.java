package com.Whitebox.ATM;

import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.model.Client;
import com.Whitebox.ATM.service.ClientService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

         @LocalServerPort
         int port;

        @Autowired
        private ClientService clientService;

        @Autowired
        private ClientDao userDao;

        @Test
        public void printUsers() {
            List<Client> clients =
                    userDao.findAll();

            System.out.println("clients = " + clients);
        }
}
