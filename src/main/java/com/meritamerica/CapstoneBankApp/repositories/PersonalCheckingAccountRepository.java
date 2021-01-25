package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.CapstoneBankApp.models.PersonalChecking;

public interface PersonalCheckingAccountRepository extends JpaRepository<PersonalChecking, Long>{

}
