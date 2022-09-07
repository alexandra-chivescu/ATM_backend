package com.Whitebox.ATM.model.dto;

import com.Whitebox.ATM.model.Banknote;

import java.util.Map;

public class WithdrawDto {
    public Map<Banknote, Integer> banknotes;

    public WithdrawDto(Map<Banknote, Integer> banknotes) {
        this.banknotes = banknotes;
    }
}
