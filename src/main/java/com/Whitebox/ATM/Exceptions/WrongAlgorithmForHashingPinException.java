package com.Whitebox.ATM.Exceptions;

import java.security.NoSuchAlgorithmException;

public class WrongAlgorithmForHashingPinException extends NoSuchAlgorithmException {
    public WrongAlgorithmForHashingPinException(String errorMessage) {
        super(errorMessage);
    }
}
