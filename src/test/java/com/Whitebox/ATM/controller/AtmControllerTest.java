package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.model.dto.BankDto;
import com.Whitebox.ATM.model.dto.ClientDepositDto;
import com.Whitebox.ATM.service.Tests;
import com.Whitebox.ATM.model.ATM;
import com.Whitebox.ATM.model.BanknoteFund;
import com.Whitebox.ATM.model.dto.AtmDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.Whitebox.ATM.model.Banknote.*;
import static io.restassured.RestAssured.given;

public class AtmControllerTest extends Tests {

    AtmDto atm;
    ClientDepositDto client;
    BankDto bank;

    @Test
    public void return_success_when_save_atm() {
        atm = new AtmDto();
        atm.setLocation("location1");
        System.out.println(atm);
        given().port(super.port)
                .contentType("application/json")
                .body(atm)
                .post("/atms/")
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void return_success_when_set_funds_for_atm() {
        atm = saveNewAtmWithBanknoteFund("Aviatiei");
        ATM atm1 = new ATM();
        atm1.setLocation("Aviatiei");
        atm1.setId(2);

        List<BanknoteFund> banknoteFunds = new ArrayList<>();
        banknoteFunds.add(new BanknoteFund(ONE_LEU, 50, atm1));
        banknoteFunds.add(new BanknoteFund(FIVE_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(TEN_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(TWENTY_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(FIFTY_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(ONE_HUNDREAD_LEI, 50, atm1));

        atm1.setBanknoteFunds(banknoteFunds);
        atm.setBanknoteFunds(banknoteFunds);

        banknoteFunds.forEach(banknoteFund ->
                banknoteFund.setAtm(atm1));

        given().port(super.port)
                .contentType("application/json")
                .body(atm)
                .post("/atms/funds")
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void return_success_when_added_funds_for_atm() {
        atm = saveNewAtm("Aviatiei");
        ATM atm1 = new ATM();
        atm1.setLocation("Aviatiei");
        atm1.setId(2);

        List<BanknoteFund> banknoteFunds = new ArrayList<>();
        banknoteFunds.add(new BanknoteFund(ONE_LEU, 50, atm1));
        banknoteFunds.add(new BanknoteFund(FIVE_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(TEN_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(TWENTY_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(FIFTY_LEI, 50, atm1));
        banknoteFunds.add(new BanknoteFund(ONE_HUNDREAD_LEI, 50, atm1));

        atm1.setBanknoteFunds(banknoteFunds);
        atm.setBanknoteFunds(banknoteFunds);

        banknoteFunds.forEach(banknoteFund ->
                banknoteFund.setAtm(atm1));

        given().port(super.port)
                .contentType("application/json")
                .body(atm)
                .patch("/atms/funds")
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void return_success_when_deposit_into_account() {
        atm = saveNewAtm("Dristor");
        bank = saveNewBank(1, "BCR");
        client = saveNewClient(1, "Alexandra", "Chivescu", "alechivescu@gmail.com", 1, "233", "1234");
        client.setAtmId(1);
        client.setAmount(500);
        client.setAccountId(1);
        given().port(super.port)
                .contentType("application/json")
                .body(client)
                .patch("/atms/deposit")
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void return_bad_request_for_deposit_of_negative_amount() {
        atm = saveNewAtm("Dristor");
        bank = saveNewBank(1, "BCR");
        client = saveNewClient(1, "Alexandra", "Chivescu", "alechivescu@gmail.com", 1, "233", "1234");
        client.setAtmId(1);
        client.setAmount(-500);
        client.setAccountId(1);
        given().port(super.port)
                .contentType("application/json")
                .body(client)
                .patch("/atms/deposit")
                .then()
                .log().all().assertThat().statusCode(400);
    }

    @Test
    public void return_success_for_possible_withdrawal() {
        atm = saveNewAtm("Dristor");
        bank = saveNewBank(1, "BCR");
        client = saveNewClient(1, "Alexandra", "Chivescu", "alechivescu@gmail.com", 1, "233", "1234");
        client.setAtmId(1);
        client.setAmount(500);
        client.setAmountToWithdraw(100);
        client.setAccountId(1);
        given().port(super.port)
                .contentType("application/json")
                .body(client)
                .patch("/atms/deposit")
                .then()
                .log().all().assertThat().statusCode(200);
    }

}
