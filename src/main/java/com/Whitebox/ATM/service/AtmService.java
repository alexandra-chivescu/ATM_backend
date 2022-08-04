package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.BalanceSmallerThanAmountToWithdrawException;
import com.Whitebox.ATM.Exceptions.NegativeAmountException;
import com.Whitebox.ATM.Exceptions.NotEnoughBanknotesInTheAtmException;
import com.Whitebox.ATM.dao.AtmDao;
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
    AccountService accountService;

    public void save(String location) {
        ATM atm = new ATM();
        atm.setLocation(location);
        atmDao.save(atm);
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

    public void withdraw(int accountId, double amount) {
        amountLessThanZeroExceptionHandle(amount);
        amountGreaterThanAccountBalanceExceptionHandle(accountId, amount);
        //TODO exception - verify if the number of the account exists
        //                 for this use numAccounts
        // if(accountId > userDao.getClientById)
        int[] values = Banknote.getValuesOfBanknotes();
        //getAmountsOfBanknotes
        int[] amounts = Banknote.getValuesOfBanknotes();

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
                System.out.println("The sum was successfully withdrawn by the user.");
                System.out.println("Left banknotes in the ATM: " + "\n" + amounts[0] + " of 1 leu" + "\n" + amounts[1] + " of 5 lei" + "\n" + amounts[2] + " of 10 lei" + "\n" + amounts[3] + " of 20 lei " + "\n" + amounts[4] + " of 50 lei" + "\n" + amounts[5] + " of 100 lei");
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
