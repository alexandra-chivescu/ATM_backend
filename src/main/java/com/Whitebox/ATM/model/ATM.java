package com.Whitebox.ATM.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "atm")
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String location;

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

    public static User mainMenu(Bank bank, Scanner scanner) {
        int idUser;
        String pin;
        User user;

        do {
            System.out.println("ATM for " + bank.getName() + " Bank");
            System.out.println("Enter user's id:");
            idUser = scanner.nextInt();
            System.out.println("Enter card's pin: ");
            pin = scanner.nextLine();

            user = bank.userAcceptCard(idUser, pin);
            if (user == null) {
                System.out.println("Incorrect credentials.\n Please try again.");
            }
        } while (user == null);

        return user;

    }

    public static void printMenu(User user, Scanner scanner) {
        user.printAccountsInfo();

        int choice;

        do {
            System.out.println("Make a choice:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Transfer");
            System.out.println("4. Quit");
            System.out.println("Please enter your choice: 1/2/3/4");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("This choice does not exist. Please choose between 1 and 4.");
            }
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                ATM.withdraw(user, scanner);
                break;
            case 2:
                ATM.deposit(user, scanner);
                break;
            case 3:
                ATM.transfer(user, scanner);
                break;
            case 4:
                scanner.nextLine();
                break;
        }

        if (choice != 4) {
            ATM.printMenu(user, scanner);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bcr = new Bank("Banca Comerciala Romana");

        User user1 = bcr.addUser("Alexandra", "Chivescu");
        User user2 = bcr.addUser("Cristian", "Diaconu");
        Account account1 = new Account(String.valueOf(AccountType.SAVINGS), user1, bcr);
        Account account2 = new Account(String.valueOf(AccountType.CHECKING), user1, bcr);
        Account account3 = new Account(String.valueOf(AccountType.CHECKING), user2, bcr);
        CreditCard creditCard1 = new CreditCard(account2, "2345", bcr);
        CreditCard creditCard2 = new CreditCard(account2, "1234", bcr);

        bcr.addAccount(account1);
        user1.addAccount(account1);
        bcr.addCreditCard(creditCard1);
        account2.addCreditCard(creditCard1);
        bcr.addCreditCard(creditCard1);
        account2.addCreditCard(creditCard2);

        User currentUser;
        while (true) {
            currentUser = ATM.mainMenu(bcr, scanner);

            ATM.printMenu(currentUser, scanner);
        }

    }

}
