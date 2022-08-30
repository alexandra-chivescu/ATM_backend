package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.WrongAlgorithmForHashingPinException;
import com.Whitebox.ATM.dao.AccountDao;
import com.Whitebox.ATM.dao.ClientDao;
import com.Whitebox.ATM.dao.CreditCardDao;
import com.Whitebox.ATM.model.Account;
import com.Whitebox.ATM.model.Bank;
import com.Whitebox.ATM.model.CreditCard;
import com.Whitebox.ATM.model.dto.ClientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class CreditCardService {

    @Autowired
    CreditCardDao creditCardDao;

    @Autowired
    RedisService redisService;

    @Autowired
    ClientDao clientDao;

    @Autowired
    AccountDao accountDao;

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

    public ClientDto verifyCardDetails(String cardNumber, String cvv, String pin) {
        CreditCard creditCard = creditCardDao.findCreditCardByCardNumber(cardNumber);
        byte[] secretPin = new byte[15];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            secretPin = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            try {
                throw new WrongAlgorithmForHashingPinException("The algorithm does not exist. ");
            } catch (WrongAlgorithmForHashingPinException ex) {
                ex.printStackTrace();
            }
        }

        Account account = accountDao.getOne(creditCard.getAccount().getId());
        if (creditCard.getCvv().equals(cvv) && Arrays.equals(creditCard.getSecretPin(), secretPin)) {
            String token = getOrGenerateToken(account.getHolder().getId());
            redisService.addTokenForClient(account.getHolder().getId(), token);
            return new ClientDto(clientDao.getOne(account.getHolder().getId()), token);
        }
        throw new InvalidParameterException("Incorrect card data or pin");
    }

    public String getOrGenerateToken(int clientId) {
        if (redisService.clientHasToken(clientId)) {
            return redisService.getTokenForClient(clientId);
        }
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
