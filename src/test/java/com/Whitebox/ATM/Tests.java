package com.Whitebox.ATM;

import com.Whitebox.ATM.model.Bank;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class Tests {
    @LocalServerPort
    public int port;

    public Bank saveBank(String name) {
        Bank bank = new Bank();
        bank.setName(name);
        given().
                port(this.port)
                .contentType("application/json")
                .body(bank)
                .post("/new/bank");

        return bank;
    }
}
