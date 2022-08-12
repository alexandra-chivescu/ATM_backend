package com.Whitebox.ATM.service;

import com.Whitebox.ATM.Exceptions.InvalidCredentialsException;
import com.Whitebox.ATM.dao.AdministratorDao;
import com.Whitebox.ATM.model.Administrator;
import com.Whitebox.ATM.security.AdministratorSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {

    @Autowired
    AdministratorDao administratorDao;

    @Autowired
    AdministratorSession administratorSession;

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
    public void verifyCredentials(String username, String password) {
        List<Administrator> administrators = getAdministratorByUsername(username);

        if(administrators.size() == 0) {
            throw new InvalidCredentialsException();
        } else if(administrators.size() > 1) {
            throw new InvalidCredentialsException();
        }

        if(administrators.size() == 1) {
            Administrator administrator = administrators.get(0);
            if(!administrator.getPassword().equals(password)) {
                throw new InvalidCredentialsException();
            } else
                administratorSession.setId(administrator.getId());
        }
    }
}
