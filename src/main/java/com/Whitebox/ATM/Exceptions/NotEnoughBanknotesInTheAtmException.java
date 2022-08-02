package com.Whitebox.ATM.Exceptions;

public class NotEnoughBanknotesInTheAtmException extends RuntimeException {
    public NotEnoughBanknotesInTheAtmException() {
        super("We are sorry. The sum cannot be withdrawn because the ATM does not have enough banknotes.");
    }
}
