package com.Whitebox.ATM.Exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("The credentials are incorrect.");
    }
}
