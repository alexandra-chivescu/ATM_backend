package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.model.*;
import com.Whitebox.ATM.model.dto.AtmDto;
import com.Whitebox.ATM.model.dto.BankDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class Tests {
    @LocalServerPort
    public int port;

    public AtmDto saveNewAtm(String location) {
        AtmDto atm = new AtmDto();
        atm.setId(2);
        atm.setLocation(location);
        given().
                port(this.port)
                .contentType("application/json")
                .body(atm)
                .post("/atms/");

        return atm;
    }

    public AtmDto saveNewAtmWithBanknoteFund(String location) {
        AtmDto atm = new AtmDto();
        ATM atm1 = new ATM();
        atm.setId(2);
        atm.setLocation(location);
        List<BanknoteFund> banknoteFunds = new ArrayList<>();
        banknoteFunds.add(new BanknoteFund(Banknote.ONE_LEU, 50, atm1));
        banknoteFunds.add(new BanknoteFund(Banknote.FIVE_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(Banknote.TEN_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(Banknote.TWENTY_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(Banknote.FIFTY_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(Banknote.ONE_HUNDREAD_LEI, 50, atm1));
        atm.setBanknoteFunds(banknoteFunds);
        given().
                port(this.port)
                .contentType("application/json")
                .body(atm)
                .post("/atms/");

        return atm;
    }

    public BankDto saveNewBank(int id, String name) {
        BankDto bank = new BankDto();
        bank.setId(id);
        bank.setName(name);
        given()
                .port(this.port)
                .contentType("application/json")
                .body(bank)
                .post("/banks");

        return bank;
    }

    public ClientDepositDto saveNewClient(int id, String firstName, String lastName, String email, int bankId, String cvv, String pin) {
        ClientDepositDto client = new ClientDepositDto();
        client.setClientId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setBankId(bankId);
        client.setCvv(cvv);
        client.setPin(pin);
        given()
                .port(this.port)
                .contentType("application/json")
                .body(client)
                .post("/clients");

        return client;
    }

}
