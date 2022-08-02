package com.Whitebox.ATM.Exceptions;

public class BalanceSmallerThanAmountToWithdrawException extends RuntimeException {
    public BalanceSmallerThanAmountToWithdrawException(double amount) {
        super(String.format("The amount (" + amount + ") is greater than the account balance. Withdraw a value smaller than the account balance."));
    }
}
