package com.Whitebox.ATM.service;

import com.Whitebox.ATM.dao.CreditCardDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CreditCardService {

    @Autowired
    CreditCardDao creditCardDao;

    public void save(Account account, Bank bank, String pin, String cvv, String expireDate) {
        CreditCard creditCard = new CreditCard(account, bank, pin, cvv);
        creditCard.setCardNumber(bank.getCreditCardNumber());
        creditCard.setAccount(account);
        creditCard.setCvv(cvv);
        creditCard.setExpirationDate(expireDate);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            creditCard.setSecretPin(md.digest(pin.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception NoSuchAlgorithmException was found.");
            e.printStackTrace();
            System.exit(1);
        }
        creditCardDao.save(creditCard);
    }

    public List<CreditCard> getListCreditCards() {

        return creditCardDao.findAll();
    }
}
