package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.InvalidCredentialsException;
import com.Whitebox.ATM.dao.AdministratorDao;
import com.Whitebox.ATM.model.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    @Autowired
    AdministratorDao administratorDao;

    public Administrator save(String username, String password) {
        Administrator administrator = new Administrator();
        administrator.setPassword(administrator.hashPassword(password));
        administrator.setUsername(username);
        administratorDao.save(administrator);
        return administrator;
    }

    public List<Administrator> getAdministratorByUsername(String username) {
        return administratorDao.findByUsername(username);
    }

    public Optional<Administrator> verifyAndGetUser(String username, String password) {
        List<Administrator> administrators = getAdministratorByUsername(username);

        if (administrators.size() != 1) {
            throw new InvalidCredentialsException();
        }

        Administrator administrator = administrators.get(0);
        if (!administrator.getPassword().equals(password)) {
            throw new InvalidCredentialsException();
        }
        return Optional.of(administrator);
    }
}
