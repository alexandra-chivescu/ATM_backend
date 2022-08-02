package com.Whitebox.ATM;

import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.dto.BankDto;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class BankControllerTest extends Tests {
    Bank bank;
    BankDto bankDto;

    @Test
    public void returnSuccessWhenSaveBankStatusCodeValidation() {
        bank = new Bank();
        bank.setId(3);
        bank.setName("Bank3");
        System.out.println(bank);
        given().port(super.port)
                .contentType("application/json")
                .body(bank)
                .post("/new/bank")
                .then()
                .log().all().assertThat().statusCode(200);
    }

    @Test
    public void returnFailure() {
        bankDto = new BankDto();
        given().port(super.port)
                .contentType("application/json")
                .body(bankDto)
                .post("/new/bank")
                .then()
                .log().all().assertThat().statusCode(406);
    }


    @Test
    public void returns_success_200_with_expected_id_and_name() {
        when().post("/new/bank").then()
                .statusCode(200)
                .body("bankDto.id",equalTo(1),"bankDto.name",equalTo("BRD"));
    }
}
