package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.PersonalChecking;

@Repository 
public interface PersonalCheckingAccountRepository extends JpaRepository<PersonalChecking, Long>{

}
