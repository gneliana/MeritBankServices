package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.CDAccount;

@Repository
public interface CDAccountRepository extends JpaRepository<CDAccount, Integer>{

}
