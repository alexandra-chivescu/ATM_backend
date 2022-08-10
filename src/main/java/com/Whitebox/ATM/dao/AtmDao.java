package com.Whitebox.ATM.dao;

import com.Whitebox.ATM.model.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmDao extends JpaRepository<ATM, Integer> {
}
