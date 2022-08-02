package com.Whitebox.ATM;

import com.Whitebox.ATM.Exceptions.BalanceSmallerThanAmountToWithdrawException;
import com.Whitebox.ATM.Exceptions.NegativeAmountException;
import com.Whitebox.ATM.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<Object> handleNegativeAmountException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "The amount is negative.");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BalanceSmallerThanAmountToWithdrawException.class)
    public ResponseEntity<Object> handleBalanceLessThanAmountToWithdrawException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "The amount you are trying to withdraw is greater that the account balance.");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Resource not found.");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
