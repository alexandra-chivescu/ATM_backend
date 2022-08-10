package com.Whitebox.ATM.Exceptions;

public class NegativeAmountException extends RuntimeException{
    public NegativeAmountException(double amount) {
        super(String.format("The amount (" + amount + ") cannot be negative."));
    }
}
