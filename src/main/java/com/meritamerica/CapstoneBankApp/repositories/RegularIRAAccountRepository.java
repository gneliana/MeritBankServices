package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.RegularIRA;

@Repository 
public interface RegularIRAAccountRepository extends JpaRepository<RegularIRA, Integer>{

}
