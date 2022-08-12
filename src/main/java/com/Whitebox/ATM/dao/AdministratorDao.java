package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdministratorDao extends JpaRepository<Administrator, Integer> {
    List<Administrator> findByUsername(String username);
}
