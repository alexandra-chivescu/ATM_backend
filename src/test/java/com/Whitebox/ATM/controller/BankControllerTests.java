package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.service.Tests;
import com.Whitebox.ATM.model.dto.BankDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BankControllerTests extends Tests {

    ClientDepositDto client;
    BankDto bank;

    @Test
    public void return_success_when_save_client() {
        bank = saveNewBank(1, "BCR");

        client = new ClientDepositDto();
        client.setClientId(1);
        client.setFirstName("Alexandra");
        client.setLastName("Chivescu");
        client.setEmail("alechivescu@gmail.com");
        client.setBankId(1);
        client.setCvv("233");
        client.setPin("1234");
        System.out.println(client);
        given().port(super.port)
                .contentType("application/json")
                .body(client)
                .post("/clients")
                .then()
                .log().all().assertThat().statusCode(200);
    }
}
