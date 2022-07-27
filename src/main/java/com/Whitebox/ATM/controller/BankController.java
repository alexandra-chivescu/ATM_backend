package com.Whitebox.ATM.controller;

import com.Whitebox.ATM.dao.BankDao;
import com.Whitebox.ATM.model.dto.BankDto;
import com.Whitebox.ATM.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@ResponseBody
public class BankController {

    @Autowired
    BankDao bankDao;

    @Autowired
    BankService bankService;

    @PostMapping("/createBank")
    public String saveNewBank(@RequestBody BankDto bankDto) {
        bankService.save(bankDto.name);
        return "New bank successfully created.";
    }
}
