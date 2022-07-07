package com.company;

import java.util.Scanner;

public class ATM {

    public static void withdraw(User user, Scanner scanner) {
        int fromAccount;
        double amount, accountBalance;

        do {
            System.out.printf("Enter the number of the account (between 1 and %d) to withdraw from:\n", user.numAccounts());
            fromAccount = scanner.nextInt() - 1;
            if(fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while(fromAccount < 0 || fromAccount>= user.numAccounts());

        accountBalance = user.getAccountBalance(fromAccount);

        do {
            System.out.printf("Enter the amount to withdraw (max. $%.02f): $", accountBalance);
            amount = scanner.nextFloat();
            if (amount <= 0) {
                System.out.println("You cannot withdraw a negative amount of money.");
            } else
            if(amount > accountBalance) {
                System.out.printf("Amount shouldn't be greater than balance of $%.02f. \n", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        user.addAccountTransaction(fromAccount, -1*amount);
    }

    public static void deposit(User user, Scanner scanner) {
        int toAccount;
        double amount, accountBalance = 0;

        do {
            System.out.printf("Enter the number of the account (between 1 and %d) to deposit in:\n", user.numAccounts());
            toAccount = scanner.nextInt() - 1;
            if(toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while(toAccount < 0 || toAccount >= user.numAccounts());

        do {
            System.out.printf("Enter the amount to deposit (max. $%.02f): $", accountBalance);
            amount = scanner.nextFloat();
            if (amount < 0) {
                System.out.println("You cannot transfer a negative amount of money.");
            }
        } while (amount < 0 );

        user.addAccountTransaction(toAccount, amount);
    }

    public static void transfer(User user, Scanner scanner) {
        int fromAccount, toAccount;
        double amount, accountBalance;

        do {
            System.out.printf("Enter the number of the account (between 1 and %d) to transfer from:\n", user.numAccounts());
            fromAccount = scanner.nextInt() - 1;
            if(fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while(fromAccount < 0 || fromAccount>= user.numAccounts());

        accountBalance = user.getAccountBalance(fromAccount);

        do {
            System.out.printf("Enter the number of the account (between 1 and %d) to transfer to:\n", user.numAccounts());
            toAccount = scanner.nextInt() - 1;
            if(toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Invalid. Please try again.");
            }
        } while(toAccount < 0 || toAccount>= user.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max. $%.02f): $", accountBalance);
            amount = scanner.nextFloat();
            if (amount <= 0) {
                System.out.println("You cannot transfer a negative amount of money.");
            } else
                if(amount > accountBalance) {
                    System.out.printf("Amount shouldn't be greater than balance of $%.02f. \n", accountBalance);
                }
        } while (amount < 0 || amount > accountBalance);

        user.addAccountTransaction(fromAccount, -1*amount);
        user.addAccountTransaction(toAccount, amount);

    }

    public static User mainMenu(Bank bcr, Scanner scanner) {
        String idUser;
        String pin;
        User user;

        do {
            System.out.printf("ATM for %s Bank", bcr.getName());
            System.out.println("Enter user's id:");
            idUser = scanner.nextLine();
            System.out.println("Enter card's pin: ");
            pin = scanner.nextLine();

            user = bcr.userAcceptCard(idUser, pin);
            if(user == null) {
                System.out.println("Incorrect credentials.\n Please try again.");
            }
        } while (user == null);

        return user;

    }

    public static void printMenu (User user, Scanner scanner) {
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

            if( choice < 1 || choice > 4) {
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
        }

        if(choice != 4) {
            ATM.printMenu(user, scanner);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bcr = new Bank("Banca Comerciala Romana");

        User user1 = bcr.addUser("Alexandra", "Chivescu", "12345");
        Account account1 = new Account(String.valueOf(TypesAccounts.Savings), user1, bcr);
        user1.addAccount(account1);
        bcr.addAccount(account1);

        User currentUser;
        while(true) {
            currentUser = ATM.mainMenu(bcr, scanner);

            ATM.printMenu(currentUser, scanner);
        }

    }

}
