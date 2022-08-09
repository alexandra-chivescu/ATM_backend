package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.BalanceSmallerThanAmountToWithdrawException;
import com.Whitebox.ATM.Exceptions.NegativeAmountException;
import com.Whitebox.ATM.Exceptions.NotEnoughBanknotesInTheAtmException;
import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.dao.BanknoteFundDao;
import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtmService {

    @Autowired
    AtmDao atmDao;

    @Autowired
    ClientDao clientDao;

    @Autowired
    AccountService accountService;

    @Autowired
    BanknoteFundDao banknoteFundDao;

    public void save(String location) {
        ATM atm = new ATM();
        atm.setLocation(location);
        atmDao.save(atm);
    }

    public ATM setFunds(int atmId, List<BanknoteFund> banknoteFunds) {
        ATM atm = atmDao.findById(atmId).get();
        atm.setBanknoteFunds(banknoteFunds);
        banknoteFunds.forEach(banknoteFund ->
                banknoteFund.setAtm(atm));
        atmDao.save(atm);
        return atm;
    }

    public double getBalance(int accountId) {
        return accountService.getBalance(accountId);
    }

    public void deposit(int accountId, double amount) {
        amountLessThanZeroExceptionHandle(amount);
        accountService.addTransaction(accountId, amount);
    }

    public void transfer(int accountId, int toAccount, double amount) {
        amountLessThanZeroExceptionHandle(amount);
        amountGreaterThanAccountBalanceExceptionHandle(accountId, amount);
        accountService.addTransaction(accountId, -1 * amount);
        accountService.addTransaction(toAccount, amount);
    }

    public void withdraw(int accountId, double amount, int atmId) {
        amountLessThanZeroExceptionHandle(amount);
        amountGreaterThanAccountBalanceExceptionHandle(accountId, amount);

        int[] values = Banknote.getValuesOfBanknotes();
        int[] amounts = banknoteFundDao.selectAmount(atmId);

            List<Integer[]> results = withdrawSolution(values, amounts, new int[values.length], amount, 0);
            boolean isWithdrawable = true; //for the verification of the algorithm (see the left banknotes in the terminal)
            int[] copyAmounts = amounts;

            if (results.size() > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (amounts[i] - results.get(results.size() - 1)[i] < 0) {
                        isWithdrawable = false;
                        throw new NotEnoughBanknotesInTheAtmException();
                    } else
                        amounts[i] = amounts[i] - results.get(results.size() - 1)[i];
                }
                if (isWithdrawable == false)
                    amounts = copyAmounts;
            } else {
                isWithdrawable = false;
                throw new NotEnoughBanknotesInTheAtmException();
            }
            if (isWithdrawable == true) {
                for (int i = 0; i < amounts.length; i++)
                    banknoteFundDao.updateAmountAfterWithdraw(i, amounts[i], atmId);
            }
        accountService.addTransaction(accountId, -1 * amount);
    }

    public static List<Integer[]> withdrawSolution(int[] values, int[] amounts, int[] variation, double sumToWithdraw, int position) {
        List<Integer[]> list = new ArrayList<>();
        int value = compute(values, variation);
        if (value < sumToWithdraw) {
            for (int i = position; i < values.length; i++) {
                if (amounts[i] > variation[i]) {
                    int[] newvariation = variation.clone();
                    newvariation[i]++;
                    List<Integer[]> newList = withdrawSolution(values, amounts, newvariation, sumToWithdraw, i);
                    //TODO
                    if (newList != null) {
                        list.addAll(newList);
                    }
                }
            }
        } else if (value == sumToWithdraw) {
            list.add(copy(variation));
        }
        return list;
    }

    public static Integer[] copy(int[] arr) {
        Integer[] copyArr = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;

    }

    public static int compute(int[] values, int[] variation) {
        int total = 0;
        for (int i = 0; i < variation.length; i++) {
            total += values[i] * variation[i];
        }
        return total;
    }

    public void amountLessThanZeroExceptionHandle(double amount) {
        if(amount <= 0)
            throw new NegativeAmountException(amount);
    }

    public void amountGreaterThanAccountBalanceExceptionHandle(int accountId, double amount) {
        if(amount > getBalance(accountId))
            throw new BalanceSmallerThanAmountToWithdrawException(amount);
    }

}
