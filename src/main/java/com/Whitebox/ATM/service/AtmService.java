package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.AtmDao;
import com.Whitebox.ATM.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class AtmService {

    @Autowired
    AtmDao atmDao;

    public void save(String location) {
        ATM atm = new ATM();
        atm.setLocation(location);
        atmDao.save(atm);
    }

    public static Integer[] copy(int[] arr) {
        Integer[] copyArr = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;

    }


    public List<ATM> getListAtms() {
        return atmDao.findAll();
    }

    public static int compute(int[] values, int[] variation) {
        int total = 0;
        for (int i = 0; i < variation.length; i++) {
            total += values[i] * variation[i];
        }
        return total;
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
    public static void withdraw(User user, Scanner scanner) {
        int fromAccount;
        double amount, accountBalance;
        do {
            if (user.numAccounts() > 1)
                System.out.println("Enter the number of the account (between 1 and " + user.numAccounts() + " ) to withdraw from:\n");
            else
                System.out.println("Enter the number of the account (1) to withdraw from:\n");
            fromAccount = scanner.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while (fromAccount < 0 || fromAccount >= user.numAccounts());
        accountBalance = user.getAccountBalance(fromAccount);
        do {
            System.out.println("Your account balance is: " + accountBalance + ". Please enter the amount to withdraw:");
            amount = scanner.nextFloat();
            int[] values = {Banknote.ONE_LEI.getValue(), Banknote.FIVE_LEI.getValue(), Banknote.TEN_LEI.getValue(), Banknote.TWENTY_LEI.getValue(), Banknote.FIFTY_LEI.getValue(), Banknote.ONE_HUNDREAD_LEI.getValue()};
            int[] amounts = {Banknote.ONE_LEI.getAmount(), Banknote.FIVE_LEI.getAmount(), Banknote.TEN_LEI.getAmount(), Banknote.TWENTY_LEI.getAmount(), Banknote.FIFTY_LEI.getAmount(), Banknote.ONE_HUNDREAD_LEI.getAmount()};

            List<Integer[]> results = withdrawSolution(values, amounts, new int[values.length], amount, 0);
            boolean isWithdrawable = true;
            int[] copyAmounts = amounts;

            if (results.size() > 0) {
                for (int i = 0; i < values.length; i++) {
                    if (amounts[i] - results.get(results.size() - 1)[i] < 0) {
                        System.out.println("We are sorry. The sum cannot be withdrawn because the ATM does not have enough banknotes.");
                        isWithdrawable = false;
                    } else
                        amounts[i] = amounts[i] - results.get(results.size() - 1)[i];
                }
                if (isWithdrawable == false)
                    amounts = copyAmounts;
            } else {
                System.out.println("We are sorry. The sum cannot be withdrawn because the ATM does not have enough banknotes.");
                isWithdrawable = false;
            }
            if (isWithdrawable == true) {
                System.out.println("The sum was successfully withdrawn by the user.");
                System.out.println("Left banknotes in the ATM: " + "\n" + amounts[0] + " of 1 leu" + "\n" + amounts[1] + " of 5 lei" + "\n" + amounts[2] + " of 10 lei" + "\n" + amounts[3] + " of 20 lei " + "\n" + amounts[4] + " of 50 lei" + "\n" + amounts[5] + " of 100 lei");
            }
            if (amount <= 0) {
                System.out.println("You cannot withdraw a negative amount of money. ");
            } else if (amount > accountBalance) {
                System.out.println("Amount shouldn't be greater than balance of " + accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);
        user.addAccountTransaction(fromAccount, -1 * amount);

    }

    public static void deposit(User user, Scanner scanner) {
        int toAccount;
        double amount, accountBalance = 0;

        do {
            if (user.numAccounts() > 1)
                System.out.println("Enter the number of the account (between 1 and " + user.numAccounts() + ") to deposit in:\n");
            else System.out.println("Enter the number of the account (1) to deposit in:\n");
            toAccount = scanner.nextInt() - 1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while (toAccount < 0 || toAccount >= user.numAccounts());

        do {
            System.out.println("Your account balance is:" + accountBalance + ". Enter the amount to deposit: ");
            amount = scanner.nextFloat();
            if (amount < 0) {
                System.out.println("You cannot transfer a negative amount of money.");
            }
        } while (amount < 0);

        user.addAccountTransaction(toAccount, amount);
    }

    public static void transfer(User user, Scanner scanner) {
        int fromAccount, toAccount;
        double amount, accountBalance;

        do {
            System.out.println("Enter the number of the account (between 1 and " + user.numAccounts() + "to transfer from:\n");
            fromAccount = scanner.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while (fromAccount < 0 || fromAccount >= user.numAccounts());

        accountBalance = user.getAccountBalance(fromAccount);

        do {
            System.out.println("Enter the number of the account (between 1 and " + user.numAccounts() + "to transfer to:\n");
            toAccount = scanner.nextInt() - 1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while (toAccount < 0 || toAccount >= user.numAccounts());

        do {
            System.out.println("Your account balance is: " + accountBalance + ". What amount would you like to transfer?\n");
            amount = scanner.nextFloat();
            if (amount <= 0) {
                System.out.println("You cannot transfer a negative amount of money.");
            } else if (amount > accountBalance) {
                System.out.println("Amount shouldn't be greater than balance of $" + accountBalance + "\n");
            }
        } while (amount < 0 || amount > accountBalance);

        user.addAccountTransaction(fromAccount, -1 * amount);
        user.addAccountTransaction(toAccount, amount);

    }
}
