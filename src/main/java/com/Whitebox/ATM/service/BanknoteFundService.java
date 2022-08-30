package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.NegativeAmountException;
import com.Whitebox.ATM.dao.BanknoteFundDao;
import com.Whitebox.ATM.model.Banknote;
import com.Whitebox.ATM.model.BanknoteFund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanknoteFundService {

    @Autowired
    BanknoteFundDao banknoteFundDao;

    @Autowired
    AtmService atmService;

    public List <BanknoteFund> addFunds(List<BanknoteFund> banknoteFunds, int atmId) {
        banknoteFunds.forEach(banknoteFund ->
                addAmount(banknoteFund.getBanknote(), banknoteFund.getAmount(), atmId));
        return getBanknoteFunds(atmId);
    }

    public List<BanknoteFund> getBanknoteFunds(int atmId) {
        return banknoteFundDao.findByAtmId(atmId);
    }

    public void addAmount(Banknote banknote, int amount, int atm_id) {
        if(amount < 0) {
            throw new NegativeAmountException(amount);
        }
        banknoteFundDao.updateAmount(banknote.ordinal(), amount, atm_id);
    }

}
